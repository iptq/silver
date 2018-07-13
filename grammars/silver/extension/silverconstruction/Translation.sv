grammar silver:extension:silverconstruction;

imports silver:reflect;

function translate
Expr ::= loc::Location ast::AST
{
  ast.givenLocation = loc;
  return ast.translation;
}

{--
 - This attribute transforms an AST representing a piece of Silver code into a Silver
 - Expr constructing the abstract syntax of that code.  Escape productions wrapping
 - Silver Exprs that should be included directly in the translation are handled
 - specially by reifying their contents.
 -}
synthesized attribute translation<a>::a;

synthesized attribute foundLocation::Maybe<Location>;
autocopy attribute givenLocation::Location;

attribute givenLocation, translation<Expr> occurs on AST;

aspect production nonterminalAST
top::AST ::= prodName::String children::ASTs annotations::NamedASTs
{
  production givenLocation::Location =
    fromMaybe(top.givenLocation, orElse(children.foundLocation, annotations.foundLocation));
  top.translation =
    -- "Direct" escape productions
    if
      containsBy(
        stringEq, prodName,
        ["silver:extension:silverconstruction:escapeExpr",
         "silver:extension:silverconstruction:escapeTypeExpr",
         "silver:extension:silverconstruction:escapeQName",
         "silver:extension:silverconstruction:escapeName"])
    then
      case children of
      | consAST(_, consAST(_, consAST(a, consAST(_, nilAST())))) ->
          case reify(a) of
          | right(e) -> e
          | left(msg) -> error(s"Error in reifying child of production ${prodName}:\n${msg}")
          end
      | _ -> error(s"Unexpected escape production arguments: ${show(80, top.pp)}")
      end
    -- "Indirect" escape productions
    else case prodName, children, annotations of
    | "silver:extension:silverconstruction:escape_qName",
      consAST(a, nilAST()), consNamedAST(namedAST("core:location", locAST), nilNamedAST()) ->
        case reify(a) of
        | right(e) ->
            applicationExpr(
              baseExpr(
                makeQName("silver:extension:silverconstruction:makeQName", givenLocation),
                location=givenLocation),
              '(',
              foldAppExprs(givenLocation, [e, locAST.translation]),
              ')', location=givenLocation)
        | left(msg) -> error(s"Error in reifying child of production ${prodName}:\n${msg}")
        end
    | "silver:extension:silverconstruction:escape_qName", _, _ ->
        error(s"Unexpected escape production arguments: ${show(80, top.pp)}")
    | "silver:extension:silverconstruction:escape_name",
      consAST(a, nilAST()), consNamedAST(namedAST("core:location", locAST), nilNamedAST()) ->
        case reify(a) of
        | right(e) ->
            applicationExpr(
              baseExpr(
                makeQName("silver:extension:silverconstruction:makeName", givenLocation),
                location=givenLocation),
              '(',
              foldAppExprs(givenLocation, [e, locAST.translation]),
              ')', location=givenLocation)
        | left(msg) -> error(s"Error in reifying child of production ${prodName}:\n${msg}")
        end
    | "silver:extension:silverconstruction:escape_name", _, _ ->
        error(s"Unexpected escape production arguments: ${show(80, top.pp)}")
    | _, _, _ ->
        application(
          baseExpr(makeQName(prodName, givenLocation), location=givenLocation),
          '(',
          foldAppExprs(givenLocation, reverse(children.translation)),
          ',',
          foldl(
            snocAnnoAppExprs(_, ',', _, location=givenLocation),
            emptyAnnoAppExprs(location=givenLocation),
            reverse(annotations.translation)),
          ')', location=givenLocation)
    end;
    
    children.givenLocation = givenLocation;
    annotations.givenLocation = givenLocation;
}

aspect production terminalAST
top::AST ::= terminalName::String lexeme::String location::Location
{
  local locationAST::AST = reflect(new(location));
  locationAST.givenLocation = top.givenLocation;

  top.translation =
    terminalConstructor(
      'terminal', '(',
      nominalTypeExpr(
        makeQNameType(terminalName, top.givenLocation), botlNone(location=top.givenLocation),
        location=top.givenLocation),
      ',',
      stringConst(
        terminal(String_t, s"\"${escapeString(lexeme)}\"", top.givenLocation),
        location=top.givenLocation),
      ',',
      locationAST.translation,
      ')', location=top.givenLocation);
}

aspect production listAST
top::AST ::= vals::ASTs
{
  top.translation =
    fullList(
      '[',
      foldr(
        exprsCons(_, ',', _, location=top.givenLocation),
        exprsEmpty(location=top.givenLocation),
        vals.translation),
      ']', location=top.givenLocation);
}

aspect production stringAST
top::AST ::= s::String
{
  top.translation =
    stringConst(
      terminal(String_t, s"\"${escapeString(s)}\"", top.givenLocation),
      location=top.givenLocation);
}

aspect production integerAST
top::AST ::= i::Integer
{
  top.translation =
    intConst(terminal(Int_t, toString(i), top.givenLocation), location=top.givenLocation);
}

aspect production floatAST
top::AST ::= f::Float
{
  top.translation =
    floatConst(terminal(Float_t, toString(f), top.givenLocation), location=top.givenLocation);
}

aspect production booleanAST
top::AST ::= b::Boolean
{
  top.translation =
    if b
    then trueConst('true', location=top.givenLocation)
    else falseConst('false', location=top.givenLocation);
}

aspect production anyAST
top::AST ::= x::a
{
  top.translation =
    case reflectTypeName(x) of
      just(n) -> error(s"Can't translate anyAST (type ${n})")
    | nothing() -> error("Can't translate anyAST")
    end;
}

attribute givenLocation, translation<[Expr]>, foundLocation occurs on ASTs;

aspect production consAST
top::ASTs ::= h::AST t::ASTs
{
  top.translation = h.translation :: t.translation;
  top.foundLocation =
    -- Try to reify the last child as a location
    case t of
    | nilAST() ->
        case reify(h) of
        | right(l) -> just(l)
        | left(_) -> nothing()
        end
    | _ -> t.foundLocation
    end;
}

aspect production nilAST
top::ASTs ::=
{
  top.translation = [];
  top.foundLocation = nothing();
}

attribute givenLocation, translation<[AnnoExpr]>, foundLocation occurs on NamedASTs;

aspect production consNamedAST
top::NamedASTs ::= h::NamedAST t::NamedASTs
{
  top.translation = h.translation :: t.translation;
  top.foundLocation = orElse(h.foundLocation, t.foundLocation);
}

aspect production nilNamedAST
top::NamedASTs ::=
{
  top.translation = [];
  top.foundLocation = nothing();
}

attribute givenLocation, translation<AnnoExpr>, foundLocation occurs on NamedAST;

aspect production namedAST
top::NamedAST ::= n::String v::AST
{
  top.translation =
    annoExpr(
      qNameId(makeName(last(explode(":", n)), top.givenLocation), location=top.givenLocation),
      '=',
      presentAppExpr(v.translation, location=top.givenLocation),
      location=top.givenLocation);
  top.foundLocation =
    if n == "core:location"
    then
      case reify(v) of
      | right(l) -> just(l)
      | left(msg) -> error(s"Error in reifying location:\n${msg}")
      end
    else nothing();
}

function makeName
Name ::= n::String loc::Location
{
  return
    if isUpper(head(explode("", n)))
    then nameIdUpper(terminal(IdUpper_t, n, loc), location=loc)
    else nameIdLower(terminal(IdLower_t, n, loc), location=loc);
}

function makeQName
QName ::= n::String loc::Location
{
  local ns::[Name] = map(makeName(_, loc), explode(":", n));
  return
    foldr(
      qNameCons(_, ':', _, location=loc),
      qNameId(last(ns), location=loc),
      init(ns));
}

function makeQNameType
QNameType ::= n::String loc::Location
{
  local ns::[String] = explode(":", n);
  return
    foldr(
      qNameTypeCons(_, ':', _, location=loc),
      qNameTypeId(terminal(IdUpper_t, last(ns), loc), location=loc),
      map(makeName(_, loc), init(ns)));
}
