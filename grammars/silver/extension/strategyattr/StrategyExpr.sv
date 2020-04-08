grammar silver:extension:strategyattr;

import silver:metatranslation;

annotation genName::String; -- Used to generate the names of lifted strategy attributes

autocopy attribute recVarEnv::[Pair<String String>];
inherited attribute outerAttr::Maybe<String>;
monoid attribute liftedStrategies::[Pair<String Decorated StrategyExpr>] with [], ++;
synthesized attribute attrRefName::Maybe<String>;
monoid attribute freeRecVars::[String] with [], ++;

monoid attribute matchesFrame::Boolean with false, ||;

synthesized attribute translation<a>::a;

{-
strategy attribute optimize =
  innermost(
    rule on top::StrategyExpr of
    | sequence(fail(), _) -> fail()
    | sequence(_, fail()) -> fail()
    | sequence(id(), s) -> s
    | sequence(s, id()) -> s
    | choice(fail(), s) -> s
    | choice(s, fail()) -> s
    | choice(id(), s) -> id()
    | choice(functorRef(n), s) -> functorRef(n)
    | all(id()) -> id()
    | some(fail()) -> fail()
    | one(fail()) -> fail()
    | rec(n, s) when !containsBy(stringEq, s.freeRecVars, n.name) -> s
    
    -- These don't apply inside traversals/sequence continuations!
    | all(s) when !attrMatchesChild(top.env, fromMaybe(s.genName, s.attrRefName), top.frame) -> id()
    | some(s) when !attrMatchesChild(top.env, fromMaybe(s.genName, s.attrRefName), top.frame) -> fail()
    | one(s) when !attrMatchesChild(top.env, fromMaybe(s.genName, s.attrRefName), top.frame) -> fail()
    | rewriteRule(...) when !... .matchesFrame -> fail()
    | recVarRef(n) when !n.matchesFrame -> fail()
    | strategyRef(n) when !n.matchesFrame -> fail()
    | functorRef(n) when !n.matchesFrame -> fail()
    end);
-}

nonterminal StrategyExpr with
  config, grammarName, env, location, unparse, errors, frame, compiledGrammars, flowEnv, flowDefs, -- Normal expression stuff
  genName, recVarEnv, outerAttr, liftedStrategies, attrRefName, freeRecVars,
  translation<Expr>;

flowtype StrategyExpr =
  decorate {grammarName, config, recVarEnv, outerAttr}, -- NOT frame or env
  unparse {}, errors {decorate, frame, env, compiledGrammars, flowEnv}, flowDefs {decorate, frame, env, compiledGrammars, flowEnv},
  liftedStrategies {decorate}, attrRefName {decorate},
  translation {decorate, frame, env}, freeRecVars {decorate, env};

propagate errors on StrategyExpr excluding strategyRef, functorRef;
propagate flowDefs on StrategyExpr;
propagate freeRecVars on StrategyExpr excluding rec;

aspect default production
top::StrategyExpr ::=
{
  top.attrRefName = nothing();
}

-- Basic combinators
abstract production id
top::StrategyExpr ::=
{
  top.unparse = "id";
  propagate liftedStrategies;
  top.translation = Silver_Expr { core:just($name{top.frame.signature.outputElement.elementName}) };
}

abstract production fail
top::StrategyExpr ::=
{
  top.unparse = "fail";
  propagate liftedStrategies;
  top.translation = Silver_Expr { core:nothing() };
}

abstract production sequence
top::StrategyExpr ::= s1::StrategyExpr s2::StrategyExpr
{
  top.unparse = s"(${s1.unparse} <* ${s2.unparse})";
  top.liftedStrategies :=
    s1.liftedStrategies ++
    if s2.attrRefName.isJust
    then []
    else [pair(s2.genName, s2)];
  
  s1.outerAttr = nothing();
  s2.outerAttr = nothing();
  
  local s2Name::String = fromMaybe(s2.genName, s2.attrRefName);
  top.translation =
    case s1, s2 of
    | functorRef(attr1), functorRef(attr2) ->
      Silver_Expr {
        core:just(
          decorate $name{top.frame.signature.outputElement.elementName}.$QNameAttrOccur{attr1}
          with {}.$QNameAttrOccur{attr2}) -- TODO: Decorate with all inh attributes
      }
    | functorRef(attr1), _ ->
      Silver_Expr {
        decorate $name{top.frame.signature.outputElement.elementName}.$QNameAttrOccur{attr1}
        with {}.$name{s2Name}
      }
    | _, functorRef(attr2) ->
      Silver_Expr {
        core:monad:bindMaybe(
          $Expr{s1.translation},
          \ res::$TypeExpr{typerepTypeExpr(top.frame.signature.outputElement.typerep, location=top.location)} ->
            decorate res with {}.$QNameAttrOccur{attr2}) -- TODO: Decorate with all inh attributes
      }
    | _, _ ->
      Silver_Expr {
        core:monad:bindMaybe(
          $Expr{s1.translation},
          \ res::$TypeExpr{typerepTypeExpr(top.frame.signature.outputElement.typerep, location=top.location)} ->
            decorate res with {}.$name{s2Name}) -- TODO: Decorate with all inh attributes
      }
    end;
}

abstract production choice
top::StrategyExpr ::= s1::StrategyExpr s2::StrategyExpr
{
  top.unparse = s"(${s1.unparse} <+ ${s2.unparse})";
  propagate liftedStrategies;
  
  s1.outerAttr = nothing();
  s2.outerAttr = nothing();
  
  top.translation = Silver_Expr { core:orElse($Expr{s1.translation}, $Expr{s2.translation}) };
}

-- Traversals
abstract production allTraversal
top::StrategyExpr ::= s::StrategyExpr
{
  top.unparse = s"all(${s.unparse})";
  
  top.errors <-
     case s of
     -- TBH this doesn't seem very useful anyway
     | functorRef(_) -> [err(s.location, "Functor attributes as arguments to generic traversals are not yet supported")]
     | _ -> []
     end;
  
  top.liftedStrategies :=
    if s.attrRefName.isJust
    then []
    else [pair(s.genName, s)];
  
  s.outerAttr = nothing();
  
  local sName::String = fromMaybe(s.genName, s.attrRefName);
  local sBaseName::String = last(explode(":", sName));
  local childAccesses::[Pair<String Boolean>] =
    map(
      \ e::NamedSignatureElement ->
        pair(e.elementName, attrMatchesFrame(top.env, sName, e.typerep)),
      top.frame.signature.inputElements);
  top.translation =
    {- Translation of prod::(Foo ::= a::Foo b::Integer c::Bar):
         case a.s, c.s of
         | just(a_s), just(c_s) -> just(prod(a_s, b, c_s))
         | _, _ -> nothing()
         end
       Could also be implemented as chained monadic binds.  Maybe more efficient this way? -}
    caseExpr(
      flatMap(
        \ a::Pair<String Boolean> ->
          if a.snd then [Silver_Expr { $name{a.fst}.$name{sName} }] else [],
        childAccesses),
      [matchRule(
         flatMap(
           \ a::Pair<String Boolean> ->
             if a.snd
             then
               [decorate Silver_Pattern { core:just($name{a.fst ++ "_" ++ sBaseName}) }
                with { config = top.config; env = top.env; frame = top.frame; patternVarEnv = []; }]
             else [],
           childAccesses),
         nothing(),
         Silver_Expr {
           core:just(
             $Expr{
               mkFullFunctionInvocation(
                 top.location,
                 baseExpr(qName(top.location, top.frame.fullName), location=top.location),
                 map(
                   \ a::Pair<String Boolean> ->
                     if a.snd
                     then Silver_Expr { $name{a.fst ++ "_" ++ sBaseName} }
                     else Silver_Expr { $name{a.fst} },
                   childAccesses),
                 map(
                   makeAnnoArg(top.location, top.frame.signature.outputElement.elementName, _),
                   top.frame.signature.namedInputElements))})
         },
         location=top.location)],
      Silver_Expr { core:nothing() },
      nonterminalType("core:Maybe", [top.frame.signature.outputElement.typerep]),
      location=top.location);
}

abstract production someTraversal
top::StrategyExpr ::= s::StrategyExpr
{
  top.unparse = s"some(${s.unparse})";
  
  top.errors <-
     case s of
     -- TBH this doesn't seem very useful anyway
     | functorRef(_) -> [err(s.location, "Functor attributes as arguments to generic traversals are not yet supported")]
     | _ -> []
     end;
  
  top.liftedStrategies :=
    if s.attrRefName.isJust
    then []
    else [pair(s.genName, s)];
  
  s.outerAttr = nothing();
  
  local sName::String = fromMaybe(s.genName, s.attrRefName);
  local childAccesses::[Pair<String Boolean>] =
    map(
      \ e::NamedSignatureElement ->
        pair(e.elementName, attrMatchesFrame(top.env, sName, e.typerep)),
      top.frame.signature.inputElements);
  top.translation =
    {- Translation of prod::(Foo ::= a::Foo b::Integer c::Bar):
         if a.s.isJust || c.s.isJust
         then just(prod(fromMaybe(a, a.s), b, fromMaybe(c, c.s)))
         else nothing()
       Not sure of a clean way to do this with monads -}
    Silver_Expr {
      if $Expr{
        foldr(
          or(_, '||', _, location=top.location),
          falseConst('false', location=top.location),
          map(
            \ a::String -> Silver_Expr { $name{a}.$name{sName}.isJust },
            map(fst, filter(snd, childAccesses))))}
      then
        core:just(
          $Expr{
            mkFullFunctionInvocation(
              top.location,
              baseExpr(qName(top.location, top.frame.fullName), location=top.location),
              map(
                \ a::Pair<String Boolean> ->
                  if a.snd
                  then Silver_Expr { core:fromMaybe($name{a.fst}, $name{a.fst}.$name{sName}) }
                  else Silver_Expr { $name{a.fst} },
                childAccesses),
              map(
                makeAnnoArg(top.location, top.frame.signature.outputElement.elementName, _),
                top.frame.signature.namedInputElements))})
      else core:nothing()
    };
}
abstract production oneTraversal
top::StrategyExpr ::= s::StrategyExpr
{
  top.unparse = s"one(${s.unparse})";
  
  top.errors <-
     case s of
     -- TBH this doesn't seem very useful anyway
     | functorRef(_) -> [err(s.location, "Functor attributes as arguments to generic traversals are not yet supported")]
     | _ -> []
     end;
  
  top.liftedStrategies :=
    if s.attrRefName.isJust
    then []
    else [pair(s.genName, s)];
  
  s.outerAttr = nothing();
  
  local sName::String = fromMaybe(s.genName, s.attrRefName);
  local sBaseName::String = last(explode(":", sName));
  local childAccesses::[Pair<String Boolean>] =
    map(
      \ e::NamedSignatureElement ->
        pair(e.elementName, attrMatchesFrame(top.env, sName, e.typerep)),
      top.frame.signature.inputElements);
  local matchingChildren::[String] = map(fst, filter(snd, childAccesses));
  top.translation =
    {- Translation of prod::(Foo ::= a::Foo b::Integer c::Bar):
         case a.s, c.s of
         | just(a_s), _ -> just(prod(a_s, b, c))
         | _, just(c_s) -> just(prod(a, b, c_s))
         | _, _ -> nothing()
         end
       Could also be implemented as
         orElse(
           bindMaybe(a.s, \ a_s::Foo -> returnMaybe(prod(a_s, b, c))),
           bindMaybe(c.s, \ c_s::Bar -> returnMaybe(prod(a, b, c_s)))  -}
    caseExpr(
      map(
        \ a::String -> Silver_Expr { $name{a}.$name{sName} },
        matchingChildren),
      map(
        \ i::Integer ->
          let childI::String = head(drop(i, matchingChildren))
          in let childIndex::Integer = positionOf(stringEq, childI, map(fst, childAccesses))
          in 
            matchRule(
              map(
                \ p::Pattern -> decorate p with { config = top.config; env = top.env; frame = top.frame; patternVarEnv = []; },
                repeat(wildcPattern('_', location=top.location), i) ++
                Silver_Pattern { core:just($name{childI ++ "_" ++ sBaseName}) } ::
                repeat(wildcPattern('_', location=top.location), length(matchingChildren) - (i + 1))),
              nothing(),
              Silver_Expr {
                core:just(
                  $Expr{
                    mkFullFunctionInvocation(
                      top.location,
                      baseExpr(qName(top.location, top.frame.fullName), location=top.location),
                      map(
                        \ a::Pair<String Boolean> -> Silver_Expr { $name{a.fst} },
                        take(childIndex, childAccesses)) ++
                      Silver_Expr { $name{childI ++ "_" ++ sBaseName} } ::
                      map(
                        \ a::Pair<String Boolean> -> Silver_Expr { $name{a.fst} },
                        drop(childIndex + 1, childAccesses)),
                      map(
                        makeAnnoArg(top.location, top.frame.signature.outputElement.elementName, _),
                        top.frame.signature.namedInputElements))})
              },
              location=top.location)
          end end,
          range(0, length(matchingChildren))),
      Silver_Expr { core:nothing() },
      nonterminalType("core:Maybe", [top.frame.signature.outputElement.typerep]),
      location=top.location);
}

-- Recursive strategies
abstract production rec
top::StrategyExpr ::= n::Name s::StrategyExpr
{
  top.unparse = s"rec ${n.name} -> (${s.unparse})";
  
  top.liftedStrategies :=
    if top.outerAttr.isJust
    then s.liftedStrategies
    else [pair(s.genName, s)];
  top.freeRecVars := removeBy(stringEq, n.name, s.freeRecVars);
  
  s.recVarEnv = pair(n.name, fromMaybe(s.genName, top.outerAttr)) :: top.recVarEnv;
  s.outerAttr = top.outerAttr;
  
  top.translation =
    if top.outerAttr.isJust
    then s.translation
    else Silver_Expr { $name{top.frame.signature.outputElement.elementName}.$name{s.genName} };
}

-- Rules
abstract production rewriteRule
top::StrategyExpr ::= id::Name ty::TypeExpr ml::MRuleList
{
  top.unparse = "rule on " ++ id.name ++ "::" ++ ty.unparse ++ " of " ++ ml.unparse ++ " end";
  propagate liftedStrategies;
  
  -- Pattern matching error checking (mostly) happens on what caseExpr forwards to,
  -- so we need to decorate one of those here.
  local checkExpr::Expr =
    letp(
      assignExpr(id, '::', ty, '=', errorExpr([], location=top.location), location=top.location),
      caseExpr(
        [hackExprType(ty.typerep, location=top.location)],
        ml.matchRuleList,
        errorExpr([], location=top.location),
        ty.typerep,
        location=top.location),
      location=top.location);
  checkExpr.env = top.env;
  checkExpr.flowEnv = top.flowEnv;
  checkExpr.downSubst = emptySubst();
  checkExpr.finalSubst = checkExpr.upSubst;
  checkExpr.grammarName = top.grammarName;
  checkExpr.frame = top.frame;
  checkExpr.config = top.config;
  checkExpr.compiledGrammars = top.compiledGrammars;
  
  top.errors <- checkExpr.errors;
  top.errors <-
    if !ty.typerep.isDecorable
    then [wrn(ty.location, "Only rules on nonterminals can have an effect")]
    else []; 
  
  top.flowDefs <- checkExpr.flowDefs;
  
  ml.matchRulePatternSize = 1;
  
  local res::Expr =
    caseExpr(
      [Silver_Expr { $name{top.frame.signature.outputElement.elementName} }],
      ml.translation,
      Silver_Expr { core:nothing() },
      nonterminalType("core:Maybe", [ty.typerep]),
      location=top.location);
  top.translation =
    if unify(ty.typerep, top.frame.signature.outputElement.typerep).failure
    then Silver_Expr { core:nothing() }
    else if top.frame.signature.outputElement.elementName == id.name
    then res
    else Silver_Expr {
      let $Name{id}::$TypeExpr{ty} = $name{top.frame.signature.outputElement.elementName}
      in $Expr{res}
      end
    };
}

-- Hack dummy expr with a given type
abstract production hackExprType
top::Expr ::= t::Type
{
  top.typerep = t;
  forwards to errorExpr([], location=top.location);
}

attribute matchesFrame occurs on MRuleList, MatchRule, PatternList, Pattern;
propagate matchesFrame on MRuleList, MatchRule, PatternList;

attribute translation<[AbstractMatchRule]> occurs on MRuleList;

aspect production mRuleList_one
top::MRuleList ::= m::MatchRule
{
  top.translation =
    if m.matchesFrame
    then [m.translation]
    else [];
}

aspect production mRuleList_cons
top::MRuleList ::= h::MatchRule '|' t::MRuleList
{
  top.translation =
    if h.matchesFrame
    then h.translation :: t.translation
    else t.translation;
}

attribute translation<AbstractMatchRule> occurs on MatchRule;

aspect production matchRule_c
top::MatchRule ::= pt::PatternList _ e::Expr
{
  top.translation =
    matchRule(
      pt.patternList, nothing(), Silver_Expr { core:just($Expr{e}) },
      location=top.location);
}

aspect production matchRuleWhen_c
top::MatchRule ::= pt::PatternList 'when' cond::Expr _ e::Expr
{
  top.translation =
    matchRule(
      pt.patternList, just(cond), Silver_Expr { core:just($Expr{e}) },
      location=top.location);
}

aspect default production
top::Pattern ::=
{
  top.matchesFrame := true;
}

aspect production prodAppPattern_named
top::Pattern ::= prod::QName '(' ps::PatternList ',' nps::NamedPatternList ')'
{
  top.matchesFrame := prod.lookupValue.fullName == top.frame.fullName;
}

-- References to other attributes or rec variables
abstract production nameRef
top::StrategyExpr ::= id::QName
{
  top.unparse = id.unparse;
  
  -- Forwarding depends on env here, these must be computed without env
  propagate liftedStrategies;
  top.attrRefName = just(fromMaybe(id.name, lookupBy(stringEq, id.name, top.recVarEnv)));
  
  local attrDcl::DclInfo = id.lookupAttribute.dcl;
  attrDcl.givenNonterminalType = error("Not actually needed"); -- Ugh environment needs refactoring
  forwards to
    if lookupBy(stringEq, id.name, top.recVarEnv).isJust
    then recVarRef(id, genName=top.genName, location=top.location)
    else if !null(id.lookupAttribute.errors)
    then errorRef(id.lookupAttribute.errors, id, genName=top.genName, location=top.location)
    else case decorate id.lookupAttribute.dcl with { givenNonterminalType = error("Not actually needed"); }.typerep of -- Ugh environment needs refactoring
    | nonterminalType("core:Maybe", _) -> strategyRef(qNameAttrOccur(id, location=top.location), genName=top.genName, location=top.location)
    | _ -> functorRef(qNameAttrOccur(id, location=top.location), genName=top.genName, location=top.location)
    end;
}
abstract production errorRef
top::StrategyExpr ::= msg::[Message] id::Decorated QName
{
  top.unparse = id.unparse;
  
  propagate liftedStrategies;
  top.attrRefName = just(id.name);
  
  top.errors <- msg;
  top.translation = Silver_Expr { core:nothing() };
}
abstract production recVarRef
top::StrategyExpr ::= id::Decorated QName
{
  top.unparse = id.unparse;
  
  propagate liftedStrategies;
  top.attrRefName = lookupBy(stringEq, id.name, top.recVarEnv);
  top.freeRecVars <- [id.name];
  
  top.translation = Silver_Expr { $name{top.frame.signature.outputElement.elementName}.$qName{top.attrRefName.fromJust} };
}
abstract production strategyRef
top::StrategyExpr ::= id::QNameAttrOccur
{
  top.unparse = id.unparse;
  
  local attrDcl::DclInfo = case id of qNameAttrOccur(a) -> a.lookupAttribute.dcl end;
  attrDcl.givenNonterminalType = error("Not actually needed"); -- Ugh environment needs refactoring
  top.errors :=
    case attrDcl.typerep, attrDcl.dclBoundVars of
    | nonterminalType("core:Maybe", [varType(a1)]), [a2] when tyVarEqual(a1, a2) -> []
    | nonterminalType("core:Maybe", [nonterminalType(nt, _)]), _ ->
      case getOccursDcl(attrDcl.fullName, nt, top.env) of
      | [] -> [wrn(id.location, s"Attribute ${id.name} cannot be used as a strategy, because it doesn't occur on its own nonterminal type ${nt}")]
      | _ -> []
      end
    | errorType(), _ -> []
    | _, _ -> [err(id.location, s"Attribute ${id.name} cannot be used as a strategy")]
    end;
  
  propagate liftedStrategies;
  top.attrRefName = just(id.name);
  
  id.attrFor = top.frame.signature.outputElement.typerep;
  
  top.translation =
    if id.matchesFrame
    then Silver_Expr { $name{top.frame.signature.outputElement.elementName}.$QNameAttrOccur{id} }
    else Silver_Expr { core:nothing() };
}
abstract production functorRef
top::StrategyExpr ::= id::QNameAttrOccur
{
  top.unparse = id.unparse;
  
  local attrDcl::DclInfo = case id of qNameAttrOccur(a) -> a.lookupAttribute.dcl end;
  attrDcl.givenNonterminalType = error("Not actually needed"); -- Ugh environment needs refactoring
  top.errors :=
    case attrDcl.typerep, attrDcl.dclBoundVars of
    | varType(a1), [a2] when tyVarEqual(a1, a2) -> []
    | nonterminalType(nt, _), _ ->
      case getOccursDcl(attrDcl.fullName, nt, top.env) of
      | [] -> [wrn(id.location, s"Attribute ${id.name} cannot be used as a functor, because it doesn't occur on its own nonterminal type ${nt}")]
      | _ -> []
      end
    | errorType(), _ -> []
    | _, _ -> [err(id.location, s"Attribute ${id.name} cannot be used as a functor")]
    end;
  
  propagate liftedStrategies;
  top.attrRefName = just(id.name);
  
  id.attrFor = top.frame.signature.outputElement.typerep;
  
  top.translation =
    if id.matchesFrame
    then Silver_Expr { core:just($name{top.frame.signature.outputElement.elementName}.$QNameAttrOccur{id}) }
    else Silver_Expr { core:nothing() };
}

attribute matchesFrame occurs on QNameAttrOccur;

aspect production qNameAttrOccur
top::QNameAttrOccur ::= at::QName
{
  top.matchesFrame := top.found &&
    case top.typerep of
    | nonterminalType("core:Maybe", [t]) -> !unify(top.attrFor, t).failure
    | t -> !unify(top.attrFor, t).failure
    end;
}

function attrMatchesFrame
Boolean ::= env::Decorated Env attrName::String attrFor::Type
{
  return
    decorate qNameAttrOccur(qName(loc("", -1, -1, -1, -1, -1, -1), attrName), location=loc("", -1, -1, -1, -1, -1, -1))
    with { env = env; attrFor = attrFor; }.matchesFrame;
}

function attrMatchesChild
Boolean ::= env::Decorated Env attrName::String frame::BlockContext
{
  return
    any(
      map(
        \ e::NamedSignatureElement -> attrMatchesFrame(env, attrName, e.typerep),
        frame.signature.inputElements));
}
