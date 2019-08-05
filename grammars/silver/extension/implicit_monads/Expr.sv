grammar silver:extension:implicit_monads;

attribute monadRewritten<Expr>, merrors, mtyperep, mDownSubst, mUpSubst occurs on Expr;


aspect default production
top::Expr ::=
{
  top.merrors := [];
  --top.monadRewritten = error("Attribute monadRewritten must be defined on all productions");
  --top.mtyperep = errorType();
}


aspect production errorExpr
top::Expr ::= e::[Message]
{
  top.merrors := e;
  top.mUpSubst = top.mDownSubst;
  top.mtyperep = errorType();
  top.monadRewritten = errorExpr(e, location=top.location);
}

aspect production errorReference
top::Expr ::= msg::[Message]  q::Decorated QName
{
  top.merrors := msg;
  top.mUpSubst = top.mDownSubst;
  top.mtyperep = errorType();
  top.monadRewritten = errorReference(msg, q, location=top.location);
}

aspect production childReference
top::Expr ::= q::Decorated QName
{
  top.merrors := [];
  top.mUpSubst = top.mDownSubst;
  top.mtyperep = if q.lookupValue.typerep.isDecorable
                 then ntOrDecType(q.lookupValue.typerep, freshType())
                 else q.lookupValue.typerep;
  top.monadRewritten = baseExpr(new(q), location=top.location);
}

aspect production lhsReference
top::Expr ::= q::Decorated QName
{
  top.merrors := [];
  top.mUpSubst = top.mDownSubst;
  -- An LHS is *always* a decorable (nonterminal) type.
  top.mtyperep = ntOrDecType(q.lookupValue.typerep, freshType());
  top.monadRewritten = baseExpr(new(q), location=top.location);
}

aspect production localReference
top::Expr ::= q::Decorated QName
{
  top.merrors := [];
  top.mUpSubst = top.mDownSubst;
  top.mtyperep = if q.lookupValue.typerep.isDecorable
                 then ntOrDecType(q.lookupValue.typerep, freshType())
                 else q.lookupValue.typerep;
  top.monadRewritten = baseExpr(new(q), location=top.location);
}

aspect production forwardReference
top::Expr ::= q::Decorated QName
{
  top.merrors := [];
  top.mUpSubst = top.mDownSubst;
  -- An LHS (and thus, forward) is *always* a decorable (nonterminal) type.
  top.mtyperep = ntOrDecType(q.lookupValue.typerep, freshType());
  top.monadRewritten = baseExpr(new(q), location=top.location);
}

aspect production productionReference
top::Expr ::= q::Decorated QName
{
  top.merrors := [];
  top.mUpSubst = top.mDownSubst;
  top.mtyperep = freshenCompletely(q.lookupValue.typerep);
  top.monadRewritten = baseExpr(new(q), location=top.location);
}

aspect production functionReference
top::Expr ::= q::Decorated QName
{
  top.merrors := [];
  top.mUpSubst = top.mDownSubst;
  top.mtyperep = freshenCompletely(q.lookupValue.typerep);
  top.monadRewritten = baseExpr(new(q), location=top.location);
}

aspect production globalValueReference
top::Expr ::= q::Decorated QName
{
  top.merrors := [];
  top.mUpSubst = top.mDownSubst;
  top.mtyperep = freshenCompletely(q.lookupValue.typerep);
  top.monadRewritten = baseExpr(new(q), location=top.location);
}


aspect production functionInvocation
top::Expr ::= e::Decorated Expr es::Decorated AppExprs anns::Decorated AnnoAppExprs
{
  local ne::Expr = new(e);
  ne.mDownSubst = top.mDownSubst;
  ne.env = top.env;
  ne.flowEnv = top.flowEnv;
  ne.config = top.config;
  ne.compiledGrammars = top.compiledGrammars;
  ne.grammarName = top.grammarName;
  ne.frame = top.frame;
  ne.finalSubst = top.finalSubst;
  ne.downSubst = top.downSubst;
  local nes::AppExprs = new(es);
  nes.mDownSubst = ne.mUpSubst;
  nes.flowEnv = top.flowEnv;
  nes.env = top.env;
  nes.config = top.config;
  nes.compiledGrammars = top.compiledGrammars;
  nes.grammarName = top.grammarName;
  nes.frame = top.frame;
  nes.finalSubst = top.finalSubst;
  nes.downSubst = top.downSubst;
  nes.appExprTypereps = reverse(performSubstitution(ne.mtyperep, ne.mUpSubst).inputTypes);
  nes.appExprApplied = ne.unparse;

  top.merrors := ne.merrors ++ nes.merrors;
  top.mUpSubst = nes.mUpSubst;

  local mty::Type = head(nes.monadTypesLocations).fst;
  --need to check that all our monads match
  top.merrors <- if null(nes.monadTypesLocations) ||
                   foldr(\x::Pair<Type Integer> b::Boolean -> b && monadsMatch(mty, x.fst, ne.mUpSubst).fst, 
                         true, tail(nes.monadTypesLocations))
                then []
                else [err(top.location,
                      "All monad types used monadically in a function application must match")];
  --need to check it is compatible with the function return type
  top.merrors <- if isMonad(ety.outputType)
                then if null(nes.monadTypesLocations)
                     then []
                     else if monadsMatch(ety.outputType, mty, ne.mUpSubst).fst
                          then []
                          else [err(top.location,
                                    "Return type of function is a monad which doesn't " ++
                                     "match the monads used for arguments")]
                else [];

  local ety :: Type = performSubstitution(ne.mtyperep, top.mUpSubst);

  --needs to change based on whether there are monads or not
  top.mtyperep = if null(nes.monadTypesLocations)
                 then ety.outputType
                 else if isMonad(ety.outputType)
                      then ety.outputType
                      else monadOfType(head(nes.monadTypesLocations).fst, ety.outputType);

  --whether we need to wrap the ultimate function call in monadRewritten in a Return
  local wrapReturn::Boolean = !isMonad(ety.outputType) && !null(nes.monadTypesLocations);

  {-
    Monad translation creates a lambda to apply to all the arguments
    plus the function (to get fresh names for everything), then
    creates a body that binds all the monadic arguments into the final
    function application.

    For example, if we have
       fun(a, b, c, d)
    where a and d are monadic, then we translate into
       (\a1 a2 a3 a4 f. a1 >>= (\a1. a4 >>= (\a4. f(a1, a2, a3, a4))))(a, b, c, d, fun)
    Reusing ai in the bind for the ith argument simplifies doing the
    application inside all the binds.
  -}
  --TODO also needs to deal with the case where the function is a monad
  local lambda_fun::Expr = buildMonadApplicationLambda(nes.realTypes, nes.monadTypesLocations, ety, wrapReturn, top.location);
  local expanded_args::AppExprs = snocAppExprs(new(es), ',', presentAppExpr(new(e), location=top.location),
                                               location=top.location);
  --haven't done monadRewritten on annotated ones, so ignore them
  top.monadRewritten = if null(nes.monadTypesLocations)
                       then applicationExpr(ne.monadRewritten, '(', nes.monadRewritten, ')', location=top.location)
                       else
                         case anns of
                         | emptyAnnoAppExprs() ->
                           applicationExpr(lambda_fun, '(', expanded_args, ')', location=top.location)
                         | _ ->
                           error("Monad Rewriting not defined with annotated " ++
                                 "expressions in a function application")
                         end;
}
--build the lambda to apply to all the original arguments plus the function
--we're going to assume this is only called if monadTysLocs is non-empty
function buildMonadApplicationLambda
Expr ::= realtys::[Type] monadTysLocs::[Pair<Type Integer>] funType::Type wrapReturn::Boolean loc::Location
{
  local funargs::AppExprs = buildFunArgs(length(realtys), loc);
  local params::ProductionRHS = buildMonadApplicationParams(realtys, 1, funType, loc);
  local body::Expr = buildMonadApplicationBody(monadTysLocs, funargs, head(monadTysLocs).fst, wrapReturn, loc);
  return lambdap(params, body, location=loc);
}
--build the parameters for the lambda applied to all the original arguments plus the function
function buildMonadApplicationParams
ProductionRHS ::= realtys::[Type] currentLoc::Integer funType::Type loc::Location
{
  return if null(realtys)
         then productionRHSCons(productionRHSElem(name("f", loc),
                                                  '::',
                                                  typerepTypeExpr(funType, location=loc),
                                                  location=loc),
                                productionRHSNil(location=loc),
                                location=loc)
         else productionRHSCons(productionRHSElem(name("a"++toString(currentLoc), loc),
                                                  '::',
                                                  typerepTypeExpr(dropDecorated(head(realtys)), location=loc),
                                                  --typerepTypeExpr(head(realtys), location=loc),
                                                  location=loc),
                                buildMonadApplicationParams(tail(realtys), currentLoc+1, funType, loc),
                                location=loc);
}
--build the arguments for the application inside all the binds
function buildFunArgs
AppExprs ::= currentIndex::Integer loc::Location
{
  return if currentIndex == 0
         then emptyAppExprs(location=loc)
         else snocAppExprs(buildFunArgs(currentIndex - 1, loc), ',',
                           presentAppExpr(baseExpr(qName(loc,
                                                         "a"++toString(currentIndex)),
                                                   location=loc),
                                          location=loc), location=loc);
}
--build the body of the lambda which includes all the binds
function buildMonadApplicationBody
Expr ::= monadTysLocs::[Pair<Type Integer>] funargs::AppExprs monadType::Type wrapReturn::Boolean loc::Location
{
  local sub::Expr = buildMonadApplicationBody(tail(monadTysLocs), funargs, monadType, wrapReturn, loc);
  local argty::Type = head(monadTysLocs).fst;
  local bind::Expr = monadBind(argty, loc);
  local binding::ProductionRHS = productionRHSCons(productionRHSElem(name("a"++toString(head(monadTysLocs).snd),
                                                                          loc),
                                                                     '::', 
                                                                     typerepTypeExpr(monadInnerType(argty),
                                                                                     location=loc),
                                                                     location=loc),
                                                   productionRHSNil(location=loc),
                                                   location=loc);
  local bindargs::AppExprs = snocAppExprs(
                             oneAppExprs(presentAppExpr(
                                            baseExpr(qName(loc,"a"++toString(head(monadTysLocs).snd)),
                                                     location=loc),
                                            location=loc),
                                         location=loc),
                             ',',
                              presentAppExpr(lambdap(binding, sub, location=loc),
                                             location=loc),
                              location=loc);

  local step::Expr = applicationExpr(bind, '(', bindargs, ')', location=loc);

  --the function is always going to be bound into the name "f", so we hard code that here
  local baseapp::Expr = applicationExpr(baseExpr(qName(loc, "f"), location=loc),
                                        '(', funargs, ')', location=loc);
  local funapp::Expr = if wrapReturn
                       then Silver_Expr { $Expr {monadReturn(monadType, loc)}($Expr {baseapp}) }
                       else baseapp;

  return if null(monadTysLocs)
         then funapp
         else step;
}
aspect production partialApplication
top::Expr ::= e::Decorated Expr es::Decorated AppExprs anns::Decorated AnnoAppExprs
{
  local ne::Expr = new(e);
  ne.mDownSubst = top.mDownSubst;
  ne.env = top.env;
  ne.flowEnv = top.flowEnv;
  ne.config = top.config;
  ne.compiledGrammars = top.compiledGrammars;
  ne.grammarName = top.grammarName;
  ne.frame = top.frame;
  ne.finalSubst = top.finalSubst;
  ne.downSubst = top.downSubst;
  local nes::AppExprs = new(es);
  nes.mDownSubst = top.mDownSubst;
  nes.flowEnv = top.flowEnv;
  nes.env = top.env;
  nes.config = top.config;
  nes.compiledGrammars = top.compiledGrammars;
  nes.grammarName = top.grammarName;
  nes.frame = top.frame;
  nes.finalSubst = top.finalSubst;
  nes.downSubst = top.downSubst;
  nes.appExprTypereps = es.appExprTypereps;
  nes.appExprApplied = es.appExprApplied;

  top.merrors := ne.merrors ++ nes.merrors;
  top.mUpSubst = ne.mUpSubst;

  local ety :: Type = performSubstitution(ne.mtyperep, ne.mUpSubst);

  top.mtyperep = functionType(ety.outputType, nes.missingTypereps ++ anns.partialAnnoTypereps, anns.missingAnnotations);
  top.monadRewritten = error("monadRewritten not defined on partial applications, but should be in the future");
}

aspect production errorApplication
top::Expr ::= e::Decorated Expr es::AppExprs anns::AnnoAppExprs
{
  local ne::Expr = new(e);
  ne.mDownSubst = top.mDownSubst;
  ne.env = top.env;
  ne.flowEnv = top.flowEnv;
  ne.config = top.config;
  ne.compiledGrammars = top.compiledGrammars;
  ne.grammarName = top.grammarName;
  ne.frame = top.frame;
  ne.finalSubst = top.finalSubst;
  ne.downSubst = top.downSubst;

  top.merrors := ne.merrors;

  top.mUpSubst = ne.mUpSubst;
  top.mtyperep = errorType();
  top.monadRewritten = application(new(e), '(', es, ',', anns, ')', location=top.location);
}

aspect production attributeSection
top::Expr ::= '(' '.' q::QName ')'
{
  top.merrors := [];
  top.mUpSubst = top.mDownSubst;
  top.mtyperep = functionType(freshenCompletely(q.lookupAttribute.typerep), [freshType()], []);
  top.monadRewritten = attributeSection('(', '.', q, ')', location=top.location);
}

aspect production forwardAccess
top::Expr ::= e::Expr '.' 'forward'
{
  top.merrors := e.errors;
  e.mDownSubst = top.mDownSubst;
  top.mUpSubst = e.mUpSubst;
  top.mtyperep = e.mtyperep;
  top.monadRewritten = forwardAccess(e.monadRewritten, '.', 'forward', location=top.location);
}

aspect production access
top::Expr ::= e::Expr '.' q::QNameAttrOccur
{
  e.mDownSubst = top.mDownSubst;
  forward.mDownSubst = e.mUpSubst;
  top.merrors := e.merrors ++ forward.merrors;
  top.monadRewritten = access(e.monadRewritten, '.', q, location=top.location);
}

aspect production errorAccessHandler
top::Expr ::= e::Decorated Expr  q::Decorated QNameAttrOccur
{
  top.mtyperep = errorType();
  top.mUpSubst = top.mDownSubst;
  top.merrors := [];
  top.monadRewritten = access(new(e), '.', new(q), location=top.location);
}

aspect production annoAccessHandler
top::Expr ::= e::Decorated Expr  q::Decorated QNameAttrOccur
{
  local ne::Expr = new(e);
  ne.mDownSubst = top.mDownSubst;
  ne.env = top.env;
  ne.flowEnv = top.flowEnv;
  ne.config = top.config;
  ne.compiledGrammars = top.compiledGrammars;
  ne.grammarName = top.grammarName;
  ne.frame = top.frame;
  ne.finalSubst = top.finalSubst;
  ne.downSubst = top.downSubst;

  top.mUpSubst = top.mDownSubst;
  top.mtyperep = q.typerep;
  top.merrors := [];
  top.monadRewritten = access(ne.monadRewritten, '.', new(q), location=top.location);
}

aspect production terminalAccessHandler
top::Expr ::= e::Decorated Expr  q::Decorated QNameAttrOccur
{
  local ne::Expr = new(e);
  ne.mDownSubst = top.mDownSubst;
  ne.env = top.env;
  ne.flowEnv = top.flowEnv;
  ne.config = top.config;
  ne.compiledGrammars = top.compiledGrammars;
  ne.grammarName = top.grammarName;
  ne.frame = top.frame;
  ne.finalSubst = top.finalSubst;
  ne.downSubst = top.downSubst;

  top.merrors := ne.merrors;
  top.mUpSubst = top.mDownSubst;

  top.mtyperep =
    if q.name == "lexeme" || q.name == "filename"
    then stringType()
    else if q.name == "line" || q.name == "column"
    then intType()
    else if q.name == "location"
    then nonterminalType("core:Location", [])
    else errorType();

  top.monadRewritten = access(ne.monadRewritten, '.', new(q), location=top.location);
}

aspect production synDecoratedAccessHandler
top::Expr ::= e::Decorated Expr  q::Decorated QNameAttrOccur
{
  local ne::Expr = new(e);
  ne.mDownSubst = top.mDownSubst;
  ne.env = top.env;
  ne.flowEnv = top.flowEnv;
  ne.config = top.config;
  ne.compiledGrammars = top.compiledGrammars;
  ne.grammarName = top.grammarName;
  ne.frame = top.frame;
  ne.finalSubst = top.finalSubst;
  ne.downSubst = top.downSubst;

  top.mtyperep = q.typerep;
  top.mUpSubst = top.mDownSubst;
  top.merrors := ne.merrors;
  top.monadRewritten = access(ne.monadRewritten, '.', new(q), location=top.location);
}

aspect production inhDecoratedAccessHandler
top::Expr ::= e::Decorated Expr  q::Decorated QNameAttrOccur
{
  local ne::Expr = new(e);
  ne.mDownSubst = top.mDownSubst;
  ne.env = top.env;
  ne.flowEnv = top.flowEnv;
  ne.config = top.config;
  ne.compiledGrammars = top.compiledGrammars;
  ne.grammarName = top.grammarName;
  ne.frame = top.frame;
  ne.finalSubst = top.finalSubst;
  ne.downSubst = top.downSubst;

  top.mUpSubst = top.mDownSubst;
  top.mtyperep = q.typerep;
  top.merrors := ne.merrors;
  top.monadRewritten = access(ne.monadRewritten, '.', new(q), location=top.location);
}

aspect production errorDecoratedAccessHandler
top::Expr ::= e::Decorated Expr  q::Decorated QNameAttrOccur
{
  local ne::Expr = new(e);
  ne.mDownSubst = top.mDownSubst;
  ne.env = top.env;
  ne.flowEnv = top.flowEnv;
  ne.config = top.config;
  ne.compiledGrammars = top.compiledGrammars;
  ne.grammarName = top.grammarName;
  ne.frame = top.frame;
  ne.finalSubst = top.finalSubst;
  ne.downSubst = top.downSubst;

  top.merrors := ne.merrors;
  top.mUpSubst = top.mDownSubst;
  top.mtyperep = errorType();
  top.monadRewritten = access(ne.monadRewritten, '.', new(q), location=top.location);
}


aspect production decorateExprWith
top::Expr ::= 'decorate' e::Expr 'with' '{' inh::ExprInhs '}'
{
  e.mDownSubst = top.mDownSubst;
  top.mUpSubst = e.mUpSubst;
  top.mtyperep = decoratedType(performSubstitution(e.mtyperep, e.mUpSubst));
  top.merrors := e.merrors;
  top.monadRewritten = decorateExprWith('decorate', e.monadRewritten, 'with',
                                        '{', inh, '}', location=top.location);
}


aspect production trueConst
top::Expr ::= 'true'
{
  top.mUpSubst = top.mDownSubst;
  top.mtyperep = boolType();
  top.merrors := [];
  top.monadRewritten = trueConst('true', location=top.location);
}

aspect production falseConst
top::Expr ::= 'false'
{
  top.mUpSubst = top.mDownSubst;
  top.mtyperep = boolType();
  top.merrors := [];
  top.monadRewritten = falseConst('false', location=top.location);
}

aspect production and
top::Expr ::= e1::Expr '&&' e2::Expr
{
  top.merrors := e1.merrors ++ e2.merrors;

  local ec1::TypeCheck = if isMonad(e1.mtyperep)
                         then check(monadInnerType(e1.mtyperep), boolType())
                         else check(e1.mtyperep, boolType());
  local ec2::TypeCheck = if isMonad(e2.mtyperep)
                         then check(monadInnerType(e2.mtyperep), boolType())
                         else check(e2.mtyperep, boolType());
  ec1.finalSubst = top.finalSubst;
  ec2.finalSubst = top.finalSubst;
  e1.mDownSubst = top.mDownSubst;
  e2.mDownSubst = e1.mUpSubst;
  ec1.downSubst = e2.mUpSubst;
  ec2.downSubst = ec1.upSubst;
  top.mUpSubst = ec2.upSubst;
  top.mtyperep = if isMonad(e1.mtyperep)
                then e1.mtyperep --assume it will be well-typed
                else if isMonad(e2.mtyperep)
                     then e2.mtyperep
                     else boolType();

  --we assume both have the same monad, so we only need one return
  --e1 >>= ( (\x::Bool y::M(Bool). y >>= (\z::Bool. Return(x && z))) (_, e2) )
  local bindBoth::Expr =
    Silver_Expr {
      $Expr {monadBind(e1.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\ x::Boolean y::Boolean ->
        $Expr {monadBind(e1.mtyperep, top.location)}
        (y,
         \ z::Boolean ->
          $Expr {monadReturn(e1.mtyperep, top.location)}
          (x && z)))(_, $Expr {e2.monadRewritten}))
    };
  --e1 >>= ( (\x::Bool y::Bool. Return(x && y))(_, e2) )
  local bind1::Expr =
    Silver_Expr {
      $Expr {monadBind(e1.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\ x::Boolean y::Boolean -> 
          $Expr {monadReturn(e1.mtyperep, top.location)}
         (x && y))(_, $Expr {e2.monadRewritten}))
    };
  --e2 >>= ( (\x::Bool y::Bool. Return(x && y))(e1, _) )
  local bind2::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e2.monadRewritten},
       (\ x::Boolean y::Boolean -> 
          $Expr {monadReturn(e2.mtyperep, top.location)}
         (x && y))($Expr {e1.monadRewritten}, _))
    };
  top.monadRewritten = if isMonad(e1.mtyperep)
                       then if isMonad(e2.mtyperep)
                            then bindBoth
                            else bind1
                       else if isMonad(e2.mtyperep)
                            then bind2
                            else and(e1.monadRewritten, '&&', e2.monadRewritten, location=top.location);
}

aspect production or
top::Expr ::= e1::Expr '||' e2::Expr
{
  top.merrors := e1.merrors ++ e2.merrors;

  local ec1::TypeCheck = if isMonad(e1.mtyperep)
                         then check(monadInnerType(e1.mtyperep), boolType())
                         else check(e1.mtyperep, boolType());
  local ec2::TypeCheck = if isMonad(e2.mtyperep)
                         then check(monadInnerType(e2.mtyperep), boolType())
                         else check(e2.mtyperep, boolType());
  ec1.finalSubst = top.finalSubst;
  ec2.finalSubst = top.finalSubst;
  e1.mDownSubst = top.mDownSubst;
  e2.mDownSubst = e1.mUpSubst;
  ec1.downSubst = e2.mUpSubst;
  ec2.downSubst = ec1.upSubst;
  top.mUpSubst = ec2.upSubst;
  top.mtyperep = if isMonad(e1.mtyperep)
                then e1.mtyperep --assume it will be well-typed
                else if isMonad(e2.mtyperep)
                     then e2.mtyperep
                     else boolType();

  --we assume both have the same monad, so we only need one return
  --e1 >>= ( (\x::Bool y::M(Bool). y >>= (\z::Bool. Return(x || z))) (_, e2) )
  local bindBoth::Expr =
    Silver_Expr {
      $Expr {monadBind(e1.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\ x::Boolean y::Boolean ->
        $Expr {monadBind(e1.mtyperep, top.location)}
        (y,
         \ z::Boolean ->
          $Expr {monadReturn(e1.mtyperep, top.location)}
          (x || z)))(_, $Expr {e2.monadRewritten}))
    };
  --e1 >>= ( (\x::Bool y::Bool. Return(x || y))(_, e2) )
  local bind1::Expr =
    Silver_Expr {
      $Expr {monadBind(e1.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\ x::Boolean y::Boolean -> 
          $Expr {monadReturn(e1.mtyperep, top.location)}
         (x || y))(_, $Expr {e2.monadRewritten}))
    };
  --e2 >>= ( (\x::Bool y::Bool. Return(x || y))(e1, _) )
  local bind2::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e2.monadRewritten},
       (\ x::Boolean y::Boolean -> 
          $Expr {monadReturn(e2.mtyperep, top.location)}
         (x || y))($Expr {e1.monadRewritten}, _))
    };
  top.monadRewritten = if isMonad(e1.mtyperep)
                       then if isMonad(e2.mtyperep)
                            then bindBoth
                            else bind1
                       else if isMonad(e2.mtyperep)
                            then bind2
                            else or(e1.monadRewritten, '||', e2.monadRewritten, location=top.location);
}

aspect production not
top::Expr ::= '!' e::Expr
{
  top.merrors := e.merrors;

  local ec::TypeCheck = if isMonad(e.mtyperep)
                        then check(monadInnerType(e.mtyperep), boolType())
                        else check(e.mtyperep, boolType());
  e.mDownSubst = top.mDownSubst;
  ec.downSubst = e.mUpSubst;
  top.mUpSubst = ec.upSubst;
  ec.finalSubst = top.finalSubst;
  top.mtyperep = e.mtyperep; --assume it will be well-typed

  top.monadRewritten =
    if isMonad(e.mtyperep)
    then Silver_Expr {
           $Expr {monadBind(e.mtyperep, top.location)}
            ($Expr {e.monadRewritten},
             \x::Boolean -> 
              $Expr {monadReturn(e.mtyperep, top.location)}(!x))
         }
    else not('!', e.monadRewritten, location=top.location);
}

aspect production gt
top::Expr ::= e1::Expr '>' e2::Expr
{
  top.merrors := e1.merrors ++ e2.merrors;

  local ec::TypeCheck = if isMonad(e1.mtyperep)
                        then if isMonad(e2.mtyperep)
                             then check(e1.mtyperep, e2.mtyperep)
                             else check(monadInnerType(e1.mtyperep), e2.mtyperep)
                        else if isMonad(e2.mtyperep)
                             then check(e1.mtyperep, monadInnerType(e2.mtyperep))
                             else check(e1.mtyperep, e2.mtyperep);
  ec.finalSubst = top.finalSubst;
  e1.mDownSubst = top.mDownSubst;
  e2.mDownSubst = e1.mUpSubst;
  ec.downSubst = e2.mUpSubst;
  top.mUpSubst = ec.upSubst;
  top.mtyperep = if isMonad(e1.mtyperep)
                then monadOfType(e1.mtyperep, boolType())
                else if isMonad(e2.mtyperep)
                     then monadOfType(e2.mtyperep, boolType())
                     else boolType();

  --we assume both have the same monad, so we only need one return
  --e1 >>= ( (\x y -> y >>= \z -> Return(x > z))(_, e2) )
  local bindBoth::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(e2.mtyperep, location=top.location)} ->
          $Expr {monadBind(e2.mtyperep, top.location)}
          (y,
           \z::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)} ->
            $Expr {monadReturn(e2.mtyperep, top.location)}
            (x > z))) (_, $Expr {e2.monadRewritten}))
    };
  --e1 >>= ( (\x y -> Return(x > y))(_, e2) )
  local bind1::Expr =
    Silver_Expr {
      $Expr {monadBind(e1.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(monadInnerType(e1.mtyperep), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(e2.mtyperep, location=top.location)} ->
        $Expr {monadReturn(e1.mtyperep, top.location)}
        (x > y))(_, $Expr {e2.monadRewritten}))
    };
  --e2 >>= ( (\x y -> Return(x > y))(e1, _) )
  local bind2::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e2.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(e1.mtyperep, location=top.location)}
         y::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)} ->
        $Expr {monadReturn(e2.mtyperep, top.location)}
        (x > y))($Expr {e1.monadRewritten}, _))
    };
  top.monadRewritten = if isMonad(e1.mtyperep)
                       then if isMonad(e2.mtyperep)
                            then bindBoth
                            else bind1
                       else if isMonad(e2.mtyperep)
                            then bind2
                            else gt(e1.monadRewritten, '>', e2.monadRewritten, location=top.location);
}

aspect production lt
top::Expr ::= e1::Expr '<' e2::Expr
{
  top.merrors := e1.merrors ++ e2.merrors;

  local ec::TypeCheck = if isMonad(e1.mtyperep)
                        then if isMonad(e2.mtyperep)
                             then check(e1.mtyperep, e2.mtyperep)
                             else check(monadInnerType(e1.mtyperep), e2.mtyperep)
                        else if isMonad(e2.mtyperep)
                             then check(e1.mtyperep, monadInnerType(e2.mtyperep))
                             else check(e1.mtyperep, e2.mtyperep);
  ec.finalSubst = top.finalSubst;
  e1.mDownSubst = top.mDownSubst;
  e2.mDownSubst = e1.mUpSubst;
  ec.downSubst = e2.mUpSubst;
  top.mUpSubst = ec.upSubst;
  top.mtyperep = if isMonad(e1.mtyperep)
                then monadOfType(e1.mtyperep, boolType())
                else if isMonad(e2.mtyperep)
                     then monadOfType(e2.mtyperep, boolType())
                     else boolType();

  --we assume both have the same monad, so we only need one return
  --e1 >>= ( (\x y -> y >>= \z -> Return(x < z))(_, e2) )
  local bindBoth::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(e2.mtyperep, location=top.location)} ->
          $Expr {monadBind(e2.mtyperep, top.location)}
          (y,
           \z::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)} ->
            $Expr {monadReturn(e2.mtyperep, top.location)}
            (x < z))) (_, $Expr {e2.monadRewritten}))
    };
  --e1 >>= ( (\x y -> Return(x < y))(_, e2) )
  local bind1::Expr =
    Silver_Expr {
      $Expr {monadBind(e1.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(monadInnerType(e1.mtyperep), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(e2.mtyperep, location=top.location)} ->
        $Expr {monadReturn(e1.mtyperep, top.location)}
        (x < y))(_, $Expr {e2.monadRewritten}))
    };
  --e2 >>= ( (\x y -> Return(x < y))(e1, _) )
  local bind2::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e2.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(e1.mtyperep, location=top.location)}
         y::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)} ->
        $Expr {monadReturn(e2.mtyperep, top.location)}
        (x < y))($Expr {e1.monadRewritten}, _))
    };
  top.monadRewritten = if isMonad(e1.mtyperep)
                       then if isMonad(e2.mtyperep)
                            then bindBoth
                            else bind1
                       else if isMonad(e2.mtyperep)
                            then bind2
                            else lt(e1.monadRewritten, '<', e2.monadRewritten, location=top.location);
}

aspect production gteq
top::Expr ::= e1::Expr '>=' e2::Expr
{
  top.merrors := e1.merrors ++ e2.merrors;

  local ec::TypeCheck = if isMonad(e1.mtyperep)
                        then if isMonad(e2.mtyperep)
                             then check(e1.mtyperep, e2.mtyperep)
                             else check(monadInnerType(e1.mtyperep), e2.mtyperep)
                        else if isMonad(e2.mtyperep)
                             then check(e1.mtyperep, monadInnerType(e2.mtyperep))
                             else check(e1.mtyperep, e2.mtyperep);
  ec.finalSubst = top.finalSubst;
  e1.mDownSubst = top.mDownSubst;
  e2.mDownSubst = e1.mUpSubst;
  ec.downSubst = e2.mUpSubst;
  top.mUpSubst = ec.upSubst;
  top.mtyperep = if isMonad(e1.mtyperep)
                then monadOfType(e1.mtyperep, boolType())
                else if isMonad(e2.mtyperep)
                     then monadOfType(e2.mtyperep, boolType())
                     else boolType();

  --we assume both have the same monad, so we only need one return
  --e1 >>= ( (\x y -> y >>= \z -> Return(x >= z))(_, e2) )
  local bindBoth::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(e2.mtyperep, location=top.location)} ->
          $Expr {monadBind(e2.mtyperep, top.location)}
          (y,
           \z::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)} ->
            $Expr {monadReturn(e2.mtyperep, top.location)}
            (x >= z))) (_, $Expr {e2.monadRewritten}))
    };
  --e1 >>= ( (\x y -> Return(x >= y))(_, e2) )
  local bind1::Expr =
    Silver_Expr {
      $Expr {monadBind(e1.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(monadInnerType(e1.mtyperep), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(e2.mtyperep, location=top.location)} ->
        $Expr {monadReturn(e1.mtyperep, top.location)}
        (x >= y))(_, $Expr {e2.monadRewritten}))
    };
  --e2 >>= ( (\x y -> Return(x >= y))(e1, _) )
  local bind2::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e2.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(e1.mtyperep, location=top.location)}
         y::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)} ->
        $Expr {monadReturn(e2.mtyperep, top.location)}
        (x >= y))($Expr {e1.monadRewritten}, _))
    };
  top.monadRewritten = if isMonad(e1.mtyperep)
                       then if isMonad(e2.mtyperep)
                            then bindBoth
                            else bind1
                       else if isMonad(e2.mtyperep)
                            then bind2
                            else gteq(e1.monadRewritten, '>=', e2.monadRewritten, location=top.location);
}

aspect production lteq
top::Expr ::= e1::Expr '<=' e2::Expr
{
  top.merrors := e1.merrors ++ e2.merrors;

  local ec::TypeCheck = if isMonad(e1.mtyperep)
                        then if isMonad(e2.mtyperep)
                             then check(e1.mtyperep, e2.mtyperep)
                             else check(monadInnerType(e1.mtyperep), e2.mtyperep)
                        else if isMonad(e2.mtyperep)
                             then check(e1.mtyperep, monadInnerType(e2.mtyperep))
                             else check(e1.mtyperep, e2.mtyperep);
  ec.finalSubst = top.finalSubst;
  e1.mDownSubst = top.mDownSubst;
  e2.mDownSubst = e1.mUpSubst;
  ec.downSubst = e2.mUpSubst;
  top.mUpSubst = ec.upSubst;
  top.mtyperep = if isMonad(e1.mtyperep)
                then monadOfType(e1.mtyperep, boolType())
                else if isMonad(e2.mtyperep)
                     then monadOfType(e2.mtyperep, boolType())
                     else boolType();

  --we assume both have the same monad, so we only need one return
  --e1 >>= ( (\x y -> y >>= \z -> Return(x <= z))(_, e2) )
  local bindBoth::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(e2.mtyperep, location=top.location)} ->
          $Expr {monadBind(e2.mtyperep, top.location)}
          (y,
           \z::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)} ->
            $Expr {monadReturn(e2.mtyperep, top.location)}
            (x <= z))) (_, $Expr {e2.monadRewritten}))
    };
  --e1 >>= ( (\x y -> Return(x <= y))(_, e2) )
  local bind1::Expr =
    Silver_Expr {
      $Expr {monadBind(e1.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(monadInnerType(e1.mtyperep), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(e2.mtyperep, location=top.location)} ->
        $Expr {monadReturn(e1.mtyperep, top.location)}
        (x <= y))(_, $Expr {e2.monadRewritten}))
    };
  --e2 >>= ( (\x y -> Return(x <= y))(e1, _) )
  local bind2::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e2.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(e1.mtyperep, location=top.location)}
         y::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)} ->
        $Expr {monadReturn(e2.mtyperep, top.location)}
        (x <= y))($Expr {e1.monadRewritten}, _))
    };
  top.monadRewritten = if isMonad(e1.mtyperep)
                       then if isMonad(e2.mtyperep)
                            then bindBoth
                            else bind1
                       else if isMonad(e2.mtyperep)
                            then bind2
                            else lteq(e1.monadRewritten, '<=', e2.monadRewritten, location=top.location);
}

aspect production eqeq
top::Expr ::= e1::Expr '==' e2::Expr
{
  top.merrors := e1.merrors ++ e2.merrors;

  local ec::TypeCheck = if isMonad(e1.mtyperep)
                        then if isMonad(e2.mtyperep)
                             then check(e1.mtyperep, e2.mtyperep)
                             else check(monadInnerType(e1.mtyperep), e2.mtyperep)
                        else if isMonad(e2.mtyperep)
                             then check(e1.mtyperep, monadInnerType(e2.mtyperep))
                             else check(e1.mtyperep, e2.mtyperep);
  ec.finalSubst = top.finalSubst;
  e1.mDownSubst = top.mDownSubst;
  e2.mDownSubst = e1.mUpSubst;
  ec.downSubst = e2.mUpSubst;
  top.mUpSubst = ec.upSubst;
  top.mtyperep = if isMonad(e1.mtyperep)
                then monadOfType(e1.mtyperep, boolType())
                else if isMonad(e2.mtyperep)
                     then monadOfType(e2.mtyperep, boolType())
                     else boolType();

  --we assume both have the same monad, so we only need one return
  --e1 >>= ( (\x y -> y >>= \z -> Return(x == z))(_, e2) )
  local bindBoth::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(e2.mtyperep, location=top.location)} ->
          $Expr {monadBind(e2.mtyperep, top.location)}
          (y,
           \z::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)} ->
            $Expr {monadReturn(e2.mtyperep, top.location)}
            (x == z))) (_, $Expr {e2.monadRewritten}))
    };
  --e1 >>= ( (\x y -> Return(x == y))(_, e2) )
  local bind1::Expr =
    Silver_Expr {
      $Expr {monadBind(e1.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(monadInnerType(e1.mtyperep), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(e2.mtyperep, location=top.location)} ->
        $Expr {monadReturn(e1.mtyperep, top.location)}
        (x == y))(_, $Expr {e2.monadRewritten}))
    };
  --e2 >>= ( (\x y -> Return(x == y))(e1, _) )
  local bind2::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e2.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(e1.mtyperep, location=top.location)}
         y::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)} ->
        $Expr {monadReturn(e2.mtyperep, top.location)}
        (x == y))($Expr {e1.monadRewritten}, _))
    };
  top.monadRewritten = if isMonad(e1.mtyperep)
                       then if isMonad(e2.mtyperep)
                            then bindBoth
                            else bind1
                       else if isMonad(e2.mtyperep)
                            then bind2
                            else eqeq(e1.monadRewritten, '==', e2.monadRewritten, location=top.location);
}

aspect production neq
top::Expr ::= e1::Expr '!=' e2::Expr
{
  top.merrors := e1.merrors ++ e2.merrors;

  local ec::TypeCheck = if isMonad(e1.mtyperep)
                        then if isMonad(e2.mtyperep)
                             then check(e1.mtyperep, e2.mtyperep)
                             else check(monadInnerType(e1.mtyperep), e2.mtyperep)
                        else if isMonad(e2.mtyperep)
                             then check(e1.mtyperep, monadInnerType(e2.mtyperep))
                             else check(e1.mtyperep, e2.mtyperep);
  ec.finalSubst = top.finalSubst;
  e1.mDownSubst = top.mDownSubst;
  e2.mDownSubst = e1.mUpSubst;
  ec.downSubst = e2.mUpSubst;
  top.mUpSubst = ec.upSubst;
  top.mtyperep = if isMonad(e1.mtyperep)
                then monadOfType(e1.mtyperep, boolType())
                else if isMonad(e2.mtyperep)
                     then monadOfType(e2.mtyperep, boolType())
                     else boolType();

  --we assume both have the same monad, so we only need one return
  --e1 >>= ( (\x y -> y >>= \z -> Return(x != z))(_, e2) )
  local bindBoth::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(e2.mtyperep, location=top.location)} ->
          $Expr {monadBind(e2.mtyperep, top.location)}
          (y,
           \z::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)} ->
            $Expr {monadReturn(e2.mtyperep, top.location)}
            (x != z))) (_, $Expr {e2.monadRewritten}))
    };
  --e1 >>= ( (\x y -> Return(x != y))(_, e2) )
  local bind1::Expr =
    Silver_Expr {
      $Expr {monadBind(e1.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(monadInnerType(e1.mtyperep), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(e2.mtyperep, location=top.location)} ->
        $Expr {monadReturn(e1.mtyperep, top.location)}
        (x != y))(_, $Expr {e2.monadRewritten}))
    };
  --e2 >>= ( (\x y -> Return(x != y))(e1, _) )
  local bind2::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e2.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(e1.mtyperep, location=top.location)}
         y::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)} ->
        $Expr {monadReturn(e2.mtyperep, top.location)}
        (x != y))($Expr {e1.monadRewritten}, _))
    };
  top.monadRewritten = if isMonad(e1.mtyperep)
                       then if isMonad(e2.mtyperep)
                            then bindBoth
                            else bind1
                       else if isMonad(e2.mtyperep)
                            then bind2
                            else neq(e1.monadRewritten, '!=', e2.monadRewritten, location=top.location);
}

concrete production ifThen
top::Expr ::= 'if' e1::Expr 'then' e2::Expr 'end' --this is easier than anything else to do
{
  top.unparse = "if " ++ e1.unparse  ++ " then " ++ e2.unparse ++ " end";

  local ec::TypeCheck = if isMonad(e1.mtyperep)
                        then check(monadInnerType(e1.mtyperep), boolType())
                        else check(e1.mtyperep, boolType());
  ec.finalSubst = top.finalSubst;
  e1.mDownSubst = top.mDownSubst;
  e2.mDownSubst = e1.mUpSubst;
  ec.downSubst = e2.mUpSubst;
  top.mUpSubst = ec.upSubst;

  e1.downSubst = top.downSubst;
  e2.downSubst = e1.upSubst;
  top.upSubst = e2.upSubst;

  local bind::Expr = if isMonad(e1.mtyperep)
                     then monadFail(e1.mtyperep, top.location)
                     else monadFail(e2.mtyperep, top.location);
  local attribute arg::Maybe<Expr>;
  arg = case monadFailArgument(e1.mtyperep, top.location),
             monadFailArgument(e2.mtyperep, top.location) of
        | just(x), _ -> just(x)
        | _, just(x) -> just(x)
        | _, _ -> nothing()
        end;
  local fail::Expr = Silver_Expr {
                       $Expr{bind}($Expr{case arg of | just(a) -> a end})
                     };

  forwards to if isMonad(e1.mtyperep) || isMonad(e2.mtyperep)
              then case arg of
                   | just(_) -> ifThenElse('if', e1, 'then', e2, 'else', fail, location=top.location)
                   | nothing() -> 
                     errorExpr([err(top.location, "The monad used in an if-then " ++
                                "must be able to have a Fail argument generated (the " ++
                                "argument must be Integer, Float, String, or List), which" ++
                                " is not true for " ++
                                prettyType(performSubstitution(if isMonad(e1.mtyperep)
                                                               then e1.mtyperep
                                                               else e2.mtyperep, top.finalSubst)))],
                                location=top.location)
                   end
              else errorExpr([err(top.location, "One of the expressions in " ++
                              "an if-then has to have a monad type--instead, have " ++
                              prettyType(performSubstitution(e1.mtyperep, top.finalSubst)) ++
                              " and " ++ prettyType(performSubstitution(e2.mtyperep, top.finalSubst)))],
                              location=top.location);
}

aspect production ifThenElse
top::Expr ::= 'if' e1::Expr 'then' e2::Expr 'else' e3::Expr
{
  top.merrors := e1.merrors ++ e2.merrors ++ e3.merrors;

  local ec1::TypeCheck = if isMonad(e1.mtyperep)
                         then check(monadInnerType(e1.mtyperep), boolType())
                         else check(e1.mtyperep, boolType());
  local ec2::TypeCheck = if isMonad(e3.mtyperep)
                        then if isMonad(e2.mtyperep)
                             then check(e3.mtyperep, e2.mtyperep)
                             else check(monadInnerType(e3.mtyperep), e2.mtyperep)
                        else if isMonad(e2.mtyperep)
                             then check(e3.mtyperep, monadInnerType(e2.mtyperep))
                             else check(e3.mtyperep, e2.mtyperep);
  ec1.finalSubst = top.finalSubst;
  ec2.finalSubst = top.finalSubst;
  e1.mDownSubst = top.mDownSubst;
  e2.mDownSubst = e1.mUpSubst;
  ec1.downSubst = e2.mUpSubst;
  ec2.downSubst = ec1.upSubst;
  top.mUpSubst = ec2.upSubst;

  top.mtyperep = if isMonad(e1.mtyperep)
                then if isMonad(e2.mtyperep)
                     then e2.mtyperep
                     else if isMonad(e3.mtyperep)
                          then e3.mtyperep
                          else monadOfType(e1.mtyperep, e3.mtyperep)
                else if isMonad(e2.mtyperep)
                     then e2.mtyperep
                     else e3.mtyperep;

  --To deal with the case where one type or the other might be "generic" (e.g. Maybe<a>),
  --   we want to do substitution on the types before putting them into the monadRewritten
  local e2Type::Type = performSubstitution(e2.mtyperep, top.finalSubst);
  local e3Type::Type = performSubstitution(e3.mtyperep, top.finalSubst);
  --We assume that if e2 or e3 are monads, they are the same as e1 if that is a
  --   monad and we don't allow monads to become nested.
  local cMonad::Expr =
    Silver_Expr {
      $Expr {monadBind(e1.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\c::Boolean
         x::$TypeExpr {typerepTypeExpr(dropDecorated(e2Type), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(dropDecorated(e3Type), location=top.location)} ->
         --x::$TypeExpr {typerepTypeExpr(e2Type, location=top.location)}
         --y::$TypeExpr {typerepTypeExpr(e3Type, location=top.location)} ->
         if c
         then $Expr { if isMonad(e2.mtyperep)
                      then Silver_Expr {x}
                      else Silver_Expr {$Expr {monadReturn(e1.mtyperep, top.location)}(x)} }
         else $Expr { if isMonad(e3.mtyperep)
                      then Silver_Expr {y}
                      else Silver_Expr {$Expr {monadReturn(e1.mtyperep, top.location)}(y)} })
       (_, $Expr {e2.monadRewritten}, $Expr {e3.monadRewritten}))
    };
  local cBool::Expr =
    Silver_Expr {
      if $Expr {e1.monadRewritten}
      then $Expr {if isMonad(e2.mtyperep)
                  then e2.monadRewritten
                  else if isMonad(e3.mtyperep)
                       then Silver_Expr { $Expr {monadReturn(e3.mtyperep, top.location)}($Expr {e2.monadRewritten}) }
                       else e2.monadRewritten}
      else $Expr {if isMonad(e3.mtyperep)
                  then e3.monadRewritten
                  else if isMonad(e2.mtyperep)
                       then Silver_Expr { $Expr {monadReturn(e2.mtyperep, top.location)}($Expr {e3.monadRewritten}) }
                       else e3.monadRewritten}
    };
  top.monadRewritten = if isMonad(e1.mtyperep)
                       then cMonad
                       else cBool;
} 

aspect production intConst
top::Expr ::= i::Int_t
{
  top.merrors := [];
  top.mUpSubst = top.mDownSubst;
  top.mtyperep = intType();
  top.monadRewritten = intConst(i, location=top.location);
}

aspect production floatConst
top::Expr ::= f::Float_t
{
  top.merrors := [];
  top.mUpSubst = top.mDownSubst;
  top.mtyperep = floatType();
  top.monadRewritten = floatConst(f, location=top.location);
} 

aspect production plus
top::Expr ::= e1::Expr '+' e2::Expr
{
  top.merrors := e1.merrors ++ e2.merrors;

  local ec::TypeCheck = if isMonad(e1.mtyperep)
                        then if isMonad(e2.mtyperep)
                             then check(e1.mtyperep, e2.mtyperep)
                             else check(monadInnerType(e1.mtyperep), e2.mtyperep)
                        else if isMonad(e2.mtyperep)
                             then check(e1.mtyperep, monadInnerType(e2.mtyperep))
                             else check(e1.mtyperep, e2.mtyperep);
  ec.finalSubst = top.finalSubst;
  e1.mDownSubst = top.mDownSubst;
  e2.mDownSubst = e1.mUpSubst;
  ec.downSubst = e2.mUpSubst;
  top.mUpSubst = ec.upSubst;
  top.mtyperep = if isMonad(e1.mtyperep)
                then e1.mtyperep
                else e2.mtyperep;

  --we assume both have the same monad, so we only need one return
  --e1 >>= ( (\x y -> y >>= \z -> Return(x + z))(_, e2) )
  local bindBoth::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(e2.mtyperep, location=top.location)} ->
          $Expr {monadBind(e2.mtyperep, top.location)}
          (y,
           \z::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)} ->
            $Expr {monadReturn(e2.mtyperep, top.location)}
            (x + z))) (_, $Expr {e2.monadRewritten}))
    };
  --e1 >>= ( (\x y -> Return(x + y))(_, e2) )
  local bind1::Expr =
    Silver_Expr {
      $Expr {monadBind(e1.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(monadInnerType(e1.mtyperep), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(e2.mtyperep, location=top.location)} ->
        $Expr {monadReturn(e1.mtyperep, top.location)}
        (x + y))(_, $Expr {e2.monadRewritten}))
    };
  --e2 >>= ( (\x y -> Return(x + y))(e1, _) )
  local bind2::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e2.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(e1.mtyperep, location=top.location)}
         y::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)} ->
        $Expr {monadReturn(e2.mtyperep, top.location)}
        (x + y))($Expr {e1.monadRewritten}, _))
    };
  top.monadRewritten = if isMonad(e1.mtyperep)
                       then if isMonad(e2.mtyperep)
                            then bindBoth
                            else bind1
                       else if isMonad(e2.mtyperep)
                            then bind2
                            else plus(e1.monadRewritten, '+', e2.monadRewritten, location=top.location);
}

aspect production minus
top::Expr ::= e1::Expr '-' e2::Expr
{
  top.merrors := e1.merrors ++ e2.merrors;

  local ec::TypeCheck = if isMonad(e1.mtyperep)
                        then if isMonad(e2.mtyperep)
                             then check(e1.mtyperep, e2.mtyperep)
                             else check(monadInnerType(e1.mtyperep), e2.mtyperep)
                        else if isMonad(e2.mtyperep)
                             then check(e1.mtyperep, monadInnerType(e2.mtyperep))
                             else check(e1.mtyperep, e2.mtyperep);
  ec.finalSubst = top.finalSubst;
  e1.mDownSubst = top.mDownSubst;
  e2.mDownSubst = e1.mUpSubst;
  ec.downSubst = e2.mUpSubst;
  top.mUpSubst = ec.upSubst;
  top.mtyperep = if isMonad(e1.mtyperep)
                then e1.mtyperep
                else e2.mtyperep;

  --we assume both have the same monad, so we only need one return
  --e1 >>= ( (\x y -> y >>= \z -> Return(x - z))(_, e2) )
  local bindBoth::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(e2.mtyperep, location=top.location)} ->
          $Expr {monadBind(e2.mtyperep, top.location)}
          (y,
           \z::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)} ->
            $Expr {monadReturn(e2.mtyperep, top.location)}
            (x - z))) (_, $Expr {e2.monadRewritten}))
    };
  --e1 >>= ( (\x y -> Return(x - y))(_, e2) )
  local bind1::Expr =
    Silver_Expr {
      $Expr {monadBind(e1.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(monadInnerType(e1.mtyperep), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(e2.mtyperep, location=top.location)} ->
        $Expr {monadReturn(e1.mtyperep, top.location)}
        (x - y))(_, $Expr {e2.monadRewritten}))
    };
  --e2 >>= ( (\x y -> Return(x - y))(e1, _) )
  local bind2::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e2.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(e1.mtyperep, location=top.location)}
         y::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)} ->
        $Expr {monadReturn(e2.mtyperep, top.location)}
        (x - y))($Expr {e1.monadRewritten}, _))
    };
  top.monadRewritten = if isMonad(e1.mtyperep)
                       then if isMonad(e2.mtyperep)
                            then bindBoth
                            else bind1
                       else if isMonad(e2.mtyperep)
                            then bind2
                            else minus(e1.monadRewritten, '-', e2.monadRewritten, location=top.location);
}

aspect production multiply
top::Expr ::= e1::Expr '*' e2::Expr
{
  top.merrors := e1.merrors ++ e2.merrors;

  local ec::TypeCheck = if isMonad(e1.mtyperep)
                        then if isMonad(e2.mtyperep)
                             then check(e1.mtyperep, e2.mtyperep)
                             else check(monadInnerType(e1.mtyperep), e2.mtyperep)
                        else if isMonad(e2.mtyperep)
                             then check(e1.mtyperep, monadInnerType(e2.mtyperep))
                             else check(e1.mtyperep, e2.mtyperep);
  ec.finalSubst = top.finalSubst;
  e1.mDownSubst = top.mDownSubst;
  e2.mDownSubst = e1.mUpSubst;
  ec.downSubst = e2.mUpSubst;
  top.mUpSubst = ec.upSubst;
  top.mtyperep = if isMonad(e1.mtyperep)
                then e1.mtyperep
                else e2.mtyperep;

  --we assume both have the same monad, so we only need one return
  --e1 >>= ( (\x y -> y >>= \z -> Return(x * z))(_, e2) )
  local bindBoth::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(e2.mtyperep, location=top.location)} ->
          $Expr {monadBind(e2.mtyperep, top.location)}
          (y,
           \z::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)} ->
            $Expr {monadReturn(e2.mtyperep, top.location)}
            (x * z))) (_, $Expr {e2.monadRewritten}))
    };
  --e1 >>= ( (\x y -> Return(x * y))(_, e2) )
  local bind1::Expr =
    Silver_Expr {
      $Expr {monadBind(e1.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(monadInnerType(e1.mtyperep), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(e2.mtyperep, location=top.location)} ->
        $Expr {monadReturn(e1.mtyperep, top.location)}
        (x * y))(_, $Expr {e2.monadRewritten}))
    };
  --e2 >>= ( (\x y -> Return(x * y))(e1, _) )
  local bind2::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e2.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(e1.mtyperep, location=top.location)}
         y::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)} ->
        $Expr {monadReturn(e2.mtyperep, top.location)}
        (x * y))($Expr {e1.monadRewritten}, _))
    };
  top.monadRewritten = if isMonad(e1.mtyperep)
                       then if isMonad(e2.mtyperep)
                            then bindBoth
                            else bind1
                       else if isMonad(e2.mtyperep)
                            then bind2
                            else multiply(e1.monadRewritten, '*', e2.monadRewritten, location=top.location);
}

aspect production divide
top::Expr ::= e1::Expr '/' e2::Expr
{
  top.merrors := e1.merrors ++ e2.merrors;

  local ec::TypeCheck = if isMonad(e1.mtyperep)
                        then if isMonad(e2.mtyperep)
                             then check(e1.mtyperep, e2.mtyperep)
                             else check(monadInnerType(e1.mtyperep), e2.mtyperep)
                        else if isMonad(e2.mtyperep)
                             then check(e1.mtyperep, monadInnerType(e2.mtyperep))
                             else check(e1.mtyperep, e2.mtyperep);
  ec.finalSubst = top.finalSubst;
  e1.mDownSubst = top.mDownSubst;
  e2.mDownSubst = e1.mUpSubst;
  ec.downSubst = e2.mUpSubst;
  top.mUpSubst = ec.upSubst;
  top.mtyperep = if isMonad(e1.mtyperep)
                then e1.mtyperep
                else e2.mtyperep;

  --we assume both have the same monad, so we only need one return
  --e1 >>= ( (\x y -> y >>= \z -> Return(x / z))(_, e2) )
  local bindBoth::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(e2.mtyperep, location=top.location)} ->
          $Expr {monadBind(e2.mtyperep, top.location)}
          (y,
           \z::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)} ->
            $Expr {monadReturn(e2.mtyperep, top.location)}
            (x / z))) (_, $Expr {e2.monadRewritten}))
    };
  --e1 >>= ( (\x y -> Return(x / y))(_, e2) )
  local bind1::Expr =
    Silver_Expr {
      $Expr {monadBind(e1.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(monadInnerType(e1.mtyperep), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(e2.mtyperep, location=top.location)} ->
        $Expr {monadReturn(e1.mtyperep, top.location)}
        (x / y))(_, $Expr {e2.monadRewritten}))
    };
  --e2 >>= ( (\x y -> Return(x / y))(e1, _) )
  local bind2::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e2.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(e1.mtyperep, location=top.location)}
         y::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)} ->
        $Expr {monadReturn(e2.mtyperep, top.location)}
        (x / y))($Expr {e1.monadRewritten}, _))
    };
  top.monadRewritten = if isMonad(e1.mtyperep)
                       then if isMonad(e2.mtyperep)
                            then bindBoth
                            else bind1
                       else if isMonad(e2.mtyperep)
                            then bind2
                            else divide(e1.monadRewritten, '/', e2.monadRewritten, location=top.location);
}

aspect production modulus
top::Expr ::= e1::Expr '%' e2::Expr
{
  top.merrors := e1.merrors ++ e2.merrors;

  local ec::TypeCheck = if isMonad(e1.mtyperep)
                        then if isMonad(e2.mtyperep)
                             then check(e1.mtyperep, e2.mtyperep)
                             else check(monadInnerType(e1.mtyperep), e2.mtyperep)
                        else if isMonad(e2.mtyperep)
                             then check(e1.mtyperep, monadInnerType(e2.mtyperep))
                             else check(e1.mtyperep, e2.mtyperep);
  ec.finalSubst = top.finalSubst;
  e1.mDownSubst = top.mDownSubst;
  e2.mDownSubst = e1.mUpSubst;
  ec.downSubst = e2.mUpSubst;
  top.mUpSubst = ec.upSubst;
  top.mtyperep = if isMonad(e1.mtyperep)
                then e1.mtyperep
                else e2.mtyperep;

  --we assume both have the same monad, so we only need one return
  --e1 >>= ( (\x y -> y >>= \z -> Return(x % z))(_, e2) )
  local bindBoth::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(e2.mtyperep, location=top.location)} ->
          $Expr {monadBind(e2.mtyperep, top.location)}
          (y,
           \z::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)} ->
            $Expr {monadReturn(e2.mtyperep, top.location)}
            (x % z))) (_, $Expr {e2.monadRewritten}))
    };
  --e1 >>= ( (\x y -> Return(x % y))(_, e2) )
  local bind1::Expr =
    Silver_Expr {
      $Expr {monadBind(e1.mtyperep, top.location)}
      ($Expr {e1.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(monadInnerType(e1.mtyperep), location=top.location)}
         y::$TypeExpr {typerepTypeExpr(e2.mtyperep, location=top.location)} ->
        $Expr {monadReturn(e1.mtyperep, top.location)}
        (x % y))(_, $Expr {e2.monadRewritten}))
    };
  --e2 >>= ( (\x y -> Return(x % y))(e1, _) )
  local bind2::Expr =
    Silver_Expr {
      $Expr {monadBind(e2.mtyperep, top.location)}
      ($Expr {e2.monadRewritten},
       (\x::$TypeExpr {typerepTypeExpr(e1.mtyperep, location=top.location)}
         y::$TypeExpr {typerepTypeExpr(monadInnerType(e2.mtyperep), location=top.location)} ->
        $Expr {monadReturn(e2.mtyperep, top.location)}
        (x % y))($Expr {e1.monadRewritten}, _))
    };
  top.monadRewritten =  if isMonad(e1.mtyperep)
                       then if isMonad(e2.mtyperep)
                            then bindBoth
                            else bind1
                       else if isMonad(e2.mtyperep)
                            then bind2
                            else modulus(e1.monadRewritten, '%', e2.monadRewritten, location=top.location);
}

aspect production neg
top::Expr ::= '-' e::Expr
{
  top.merrors := e.merrors;

  top.mtyperep = e.mtyperep;

  e.mDownSubst = top.mDownSubst;
  top.mUpSubst = e.mUpSubst;
  top.monadRewritten =
    if isMonad(e.mtyperep)
    then Silver_Expr {
           $Expr {monadBind(e.mtyperep, top.location)}
            ($Expr {e.monadRewritten},
             \x::$TypeExpr {typerepTypeExpr(monadInnerType(e.mtyperep), location=top.location)} ->
              $Expr {monadReturn(e.mtyperep, top.location)}(-x))
         }
    else neg('-', e.monadRewritten, location=top.location);
}

aspect production stringConst
top::Expr ::= s::String_t
{
  top.merrors := [];
  top.mUpSubst = top.mDownSubst;
  top.mtyperep = stringType();

  top.monadRewritten = stringConst(s, location=top.location);
}

aspect production plusPlus
top::Expr ::= e1::Expr '++' e2::Expr
{
  e1.mDownSubst = top.mDownSubst;
  e2.mDownSubst = e1.mUpSubst;
  top.mUpSubst = e2.mUpSubst;
{-  local result_type :: Type = if isMonad(e1.mtyperep)
                              then performSubstitution(e1.mtyperep, errCheck1.upSubst)
                              else performSubstitution(e2.mtyperep, errCheck1.upSubst);

  -- Moved from 'analysis:typechecking' because we want to use this stuff here now
  local attribute errCheck1 :: TypeCheck; errCheck1.finalSubst = top.upSubst;

  e1.downSubst = top.downSubst;
  e2.downSubst = e1.upSubst;
  errCheck1.downSubst = e2.upSubst;
  forward.downSubst = monadMatchAndSubst.snd;
  -- upSubst defined via forward :D
  local attribute monadMatchAndSubst::Pair<Boolean Substitution>;
  monadMatchAndSubst = if isMonad(e1.mtyperep) && isMonad(e2.mtyperep)
                       then monadsMatch(e1.mtyperep, e2.mtyperep, errCheck1.upSubst)
                       else pair(true, errCheck1.upSubst);

  errCheck1 = check(if isMonad(e1.mtyperep)
                    then monadInnerType(e1.mtyperep)
                    else e1.mtyperep,
                    if isMonad(e2.mtyperep)
                    then monadInnerType(e2.mtyperep)
                    else e2.mtyperep);
  local errors::[Message] = (if errCheck1.typeerror
                             then [err(top.location, "Operands to ++ must be the same concatenable type or monads of the same type. Instead they are " ++ errCheck1.leftpp ++ " and " ++ errCheck1.rightpp)]
                             else []) ++
                            if monadMatchAndSubst.fst
                            then []
                            else [err(top.location, "Two monad operands to ++ must be the same monad.  Instead they are " ++ errCheck1.leftpp ++ " and " ++ errCheck1.rightpp)];

  forwards to
    -- if the types disagree, forward to an error production instead.
    if errCheck1.typeerror
    then errorExpr(errors, location=top.location)
    else if isMonad(top.mtyperep)
         then monadInnerType(top.mtyperep).appendDispatcher(e1, e2, top.location)
         else top.mtyperep.appendDispatcher(e1, e2, top.location);
-}
}

aspect production stringPlusPlus
top::Expr ::= e1::Decorated Expr   e2::Decorated Expr
{
  local ne1::Expr = new(e1);
  ne1.mDownSubst = top.mDownSubst;
  ne1.env = top.env;
  ne1.flowEnv = top.flowEnv;
  ne1.config = top.config;
  ne1.compiledGrammars = top.compiledGrammars;
  ne1.grammarName = top.grammarName;
  ne1.frame = top.frame;
  ne1.finalSubst = top.finalSubst;
  ne1.downSubst = top.downSubst;

  local ne2::Expr = new(e2);
  ne2.mDownSubst = ne1.mUpSubst;
  ne2.env = top.env;
  ne2.flowEnv = top.flowEnv;
  ne2.config = top.config;
  ne2.compiledGrammars = top.compiledGrammars;
  ne2.grammarName = top.grammarName;
  ne2.frame = top.frame;
  ne2.finalSubst = top.finalSubst;
  ne2.downSubst = ne1.upSubst;

  top.merrors := ne1.merrors ++ ne2.merrors;
  top.mUpSubst = ne2.mUpSubst;

  --we assume both types will be compatible (any other case would have
  --   been caught in plusPlus dispatching to this)
  top.mtyperep = if isMonad(ne1.mtyperep)
                then ne1.mtyperep
                else ne2.mtyperep;
  top.monadRewritten = plusPlus(new(e1), '++', new(e2), location=top.location);
}

aspect production errorPlusPlus
top::Expr ::= e1::Decorated Expr e2::Decorated Expr
{
  --local result_type :: Type = performSubstitution(e1.mtyperep, top.mDownSubst);
  top.mUpSubst = top.mDownSubst;

  top.merrors := [];
  --  if result_type.isError then []
  --  else [err(e1.location, prettyType(result_type) ++ " is not a concatenable type.")];
  top.mtyperep = errorType();

  top.monadRewritten = plusPlus(new(e1), '++', new(e2), location=top.location);
}



synthesized attribute monadTypesLocations::[Pair<Type Integer>] occurs on AppExpr, AppExprs;
synthesized attribute realTypes::[Type] occurs on AppExpr, AppExprs;
attribute monadRewritten<AppExpr>, merrors, mDownSubst, mUpSubst occurs on AppExpr;
attribute monadRewritten<AppExprs>, merrors, mDownSubst, mUpSubst occurs on AppExprs;

aspect production missingAppExpr
top::AppExpr ::= '_'
{
  top.merrors := [];
  top.mUpSubst = top.mDownSubst;
  top.monadRewritten = missingAppExpr('_', location=top.location);
  top.realTypes = [];
  top.monadTypesLocations = [];
}
aspect production presentAppExpr
top::AppExpr ::= e::Expr
{
  top.merrors := e.merrors;

  top.realTypes = [e.mtyperep];
  top.monadTypesLocations = if isMonadic
                            then [pair(e.mtyperep, top.appExprIndex+1)]
                            else [];

  --these have an 'a' at the end of their names because of a bug where local names are not local to their grammars
  local attribute errCheck1a :: TypeCheck; errCheck1a.finalSubst = top.mUpSubst;
  local attribute errCheck2a :: TypeCheck; errCheck2a.finalSubst = top.mUpSubst;

  e.mDownSubst = top.mDownSubst;
  errCheck1a.downSubst = e.mUpSubst;
  errCheck2a.downSubst = e.mUpSubst;
  top.mUpSubst = if isMonadic
                 then errCheck2a.upSubst
                 else errCheck1a.upSubst;
  --determine whether it appears that this is supposed to take
  --   advantage of implicit monads based on types matching the
  --   expected and being monads
  local isMonadic::Boolean = isMonad(e.mtyperep) && !isMonad(top.appExprTyperep);

  errCheck1a = check(e.mtyperep, top.appExprTyperep);
  errCheck2a = check(monadInnerType(e.mtyperep), top.appExprTyperep);
  top.merrors <-
    if isMonadic
    then if !errCheck2a.typeerror
         then []
         else [err(top.location, "Argument " ++ toString(top.appExprIndex+1) ++ " of function '" ++
                top.appExprApplied ++ "' expected " ++ errCheck1a.rightpp ++
                " or a monad of " ++ errCheck1a.rightpp ++
                " but argument is of type " ++ errCheck1a.leftpp)]
    else
      if !errCheck1a.typeerror
      then []
      else [err(top.location, "Argument " ++ toString(top.appExprIndex+1) ++ " of function '" ++
                top.appExprApplied ++ "' expected " ++ errCheck1a.rightpp ++
                " or a monad of " ++ errCheck1a.rightpp ++
                " but argument is of type " ++ errCheck1a.leftpp)];

  top.monadRewritten = presentAppExpr(e.monadRewritten, location=top.location);
}

aspect production snocAppExprs
top::AppExprs ::= es::AppExprs ',' e::AppExpr
{
  top.merrors := es.merrors ++ e.merrors;

  es.mDownSubst = top.mDownSubst;
  e.mDownSubst = es.mUpSubst;
  top.mUpSubst = e.mUpSubst;

  top.realTypes = es.realTypes ++ e.realTypes;

  top.monadTypesLocations = es.monadTypesLocations ++ e.monadTypesLocations;

  top.monadRewritten = snocAppExprs(es.monadRewritten, ',', e.monadRewritten, location=top.location);
}
aspect production oneAppExprs
top::AppExprs ::= e::AppExpr
{
  top.merrors := e.merrors;

  e.mDownSubst = top.mDownSubst;
  top.mUpSubst = e.mUpSubst;

  top.realTypes = e.realTypes;

  top.monadTypesLocations = e.monadTypesLocations;

  top.monadRewritten = oneAppExprs(e.monadRewritten, location=top.location);
}
aspect production emptyAppExprs
top::AppExprs ::=
{
  top.merrors := [];

  top.mUpSubst = top.mDownSubst;

  top.realTypes = [];

  top.monadTypesLocations = [];

  top.monadRewritten = emptyAppExprs(location=top.location);
}


aspect production exprRef
top::Expr ::= e::Decorated Expr
{
  local ne::Expr = new(e);
  ne.mDownSubst = top.mDownSubst;
  ne.env = top.env;
  ne.flowEnv = top.flowEnv;
  ne.config = top.config;
  ne.compiledGrammars = top.compiledGrammars;
  ne.grammarName = top.grammarName;
  ne.frame = top.frame;
  ne.finalSubst = top.finalSubst;
  ne.downSubst = top.downSubst;

  top.merrors := ne.merrors;
  top.mUpSubst = ne.mUpSubst;
  top.mtyperep = ne.mtyperep;
  top.monadRewritten = ne.monadRewritten;
}