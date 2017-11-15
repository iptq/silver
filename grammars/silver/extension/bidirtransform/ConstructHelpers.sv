grammar silver:extension:bidirtransform;


function qAttr
QNameAttrOccur ::= loc::Location name::String
{
    return qNameAttrOccur(qName(loc, name), location=loc);
}

function emptyFunc
Expr ::= loc::Location name::String 
{
    return applicationEmpty(baseName(loc,name),'(',')', location=loc);
}

function argFunc
Expr ::= loc::Location name::String args::AppExprs
{
    return applicationExpr(baseName(loc,name), '(',args,')', location=loc);
}

function fullFunc
Expr ::= loc::Location name::String args::AppExprs annos::AnnoAppExprs
{
    return application(baseName(loc,name), '(',args,',',annos,')', location=loc);
}

function synAttr
AGDcl ::= loc::Location nme::String tyexpr::TypeExpr
{
    return attributeDclSyn('synthesized', 'attribute', name(nme, loc),
        botlNone(location=loc), '::', tyexpr, ';', location=loc);
}

function lhsDef
DefLHS ::= loc::Location s::String 
{
    return concreteDefLHS(qName(loc, s), location=loc);
}

function autocAttr
AGDcl ::= loc::Location nme::String tyexpr::TypeExpr
{
    return attributeDclAuto('autocopy', 'attribute', name(nme, loc),
        botlNone(location=loc), '::', tyexpr, ';', location=loc);
}

function annoOn
AGDcls ::= loc::Location name::String onNames::[QName]
{
    return if null(onNames) then nilAGDcls(location=loc)
        else consAGDcls(
            annotateDcl('annotation', qName(loc, name), botlNone(location=loc),
                'occurs', 'on', head(onNames), botlNone(location=loc), ';', location=loc),
            annoOn(loc, name, tail(onNames)), location=loc);

}

function attrOn
AGDcls ::= loc::Location name::String onNames::[QName]
{
    return if null(onNames) then nilAGDcls(location=loc)
        else consAGDcls(
            attributionDcl('attribute', qName(loc, name), botlNone(location=loc), 
                'occurs', 'on', head(onNames), botlNone(location=loc), ';', location=loc),
            attrOn(loc, name, tail(onNames)), location=loc);
}

abstract production joinAGDcls
top::AGDcls ::= l::AGDcls r::AGDcls
{
    forwards to 
        case l of 
            | consAGDcls(dcl,dcls) -> joinAGDcls(dcls, consAGDcls(dcl,r,location=top.location), location=top.location)
            | nilAGDcls() -> r
        end;
} 

function dclQName
(QName ::= [DclInfo]) ::= loc::Location
{
    return (\ dcl::[DclInfo] -> 
        case head(dcl) of 
          | ntDcl(_,_,s,_,_,_) -> qName(loc, s)
          | _ -> qName(loc, "") -- also error
        end
    );
}

function consAnnoAppExprs
AnnoAppExprs ::= loc::Location a::AnnoAppExprs b::AnnoAppExprs
{
    return case b of 
        | snocAnnoAppExprs(c,_,expr) -> consAnnoAppExprs(loc, snocAnnoAppExprs(a, ',', expr, location=loc), c)
        | oneAnnoAppExprs(expr) -> snocAnnoAppExprs(a, ',', expr, location=loc)
        | _ -> a
    end;
}

function emptyAspectProdSig
AspectProductionSignature ::= loc::Location ns::NamedSignature 
{
    return aspectProductionSignature(
        aspectProductionLHSFull(
            name(ns.outputElement.elementName, loc), 
            ns.outputElement.typerep, location=loc),
        '::=',
        emptyAspectProdRHS(loc, ns.inputElements), location=loc);
}

-- I've never seen this used before, but it looks like we can just use empty
-- underscores in aspect productions where we don't use a given LHS element
-- so that's what this is trying to do
--
-- Todo: we actually use rhs values sometimes so lets not do that even if we can
function emptyAspectProdRHS
AspectRHS ::= loc::Location inElements::[NamedSignatureElement]
{
    return if null(inElements) then aspectRHSElemNil(location=loc)
        else aspectRHSElemCons(aspectRHSElemNone('_', location=loc), 
            emptyAspectProdRHS(loc, tail(inElements)), location=loc);
}

function aspectProdStmt
AGDcl ::= loc::Location dcl::[DclInfo] fn::(ProductionStmt ::= String Location NamedSignature)
{
    return aspectProdStmts(loc,dcl,\ sg::String l::Location ns::NamedSignature ->
        productionStmtsSnoc(productionStmtsNil(location=loc), fn(sg,l,ns), location=loc)
    );
}

function aspectProdStmts
AGDcl ::= loc::Location dcl::[DclInfo] fn::(ProductionStmts ::= String Location NamedSignature)
{
    return if null(dcl) then emptyAGDcl(location=loc) else 
        case head(dcl) of 
            | prodDcl(sg,l,ns) -> aspectProductionDcl('aspect', 'production', 
                qName(loc, ns.fullName), emptyAspectProdSig(loc, ns), 
                productionBody('{', 
                    fn(sg,l,ns),
                '}', location=loc), location=loc)
            | _ -> emptyAGDcl(location=loc)
        end;
}

function prdStmtList
ProductionStmts ::= loc::Location stmts::[ProductionStmt]
{
    return if null(stmts) then productionStmtsNil(location=loc)
        else productionStmtsSnoc(prdStmtList(loc, tail(stmts)), head(stmts), location=loc);
}

function prdBody
ProductionBody ::= loc::Location stmts::[ProductionStmt]
{
    return productionBody('{', prdStmtList(loc, stmts), '}', location=loc);
}

function attribDef
ProductionStmt ::= loc::Location lhs::String att::String eqs::Expr
{
    return attributeDef(
        lhsDef(loc, lhs), 
        '.',
        qAttr(loc, att), 
        '=', 
        eqs, 
        ';', 
        location=loc);
}

function qnTyId
QNameType ::= loc::Location s::String 
{
    return qNameTypeId(terminal(IdUpper_t, s), location=loc);
}

function sTyExpr
TypeExpr ::= loc::Location s::String 
{
    return nominalTypeExpr(qNameTypeId(terminal(IdUpper_t, s), location=loc),
        botlNone(location=loc), location=loc);
}

function qTyExpr
TypeExpr ::= loc::Location q::QName 
{
    return sTyExpr(loc, q.name);
}

function lhsAccess
AppExpr ::= loc::Location name::String ns::NamedSignature 
{
    return namedAccess(loc, name, ns.outputElement.elementName);
}  

function namedAccess
AppExpr ::= loc::Location name::String accessOn::String 
{
    return presentAppExpr(exprAccess(loc,name,accessOn), location=loc);
} 

function lhsExprAccess
Expr ::= loc::Location name::String ns::NamedSignature
{
    return exprAccess(loc, name, ns.outputElement.elementName);
}

function exprAccess
Expr ::= loc::Location name::String accessOn::String 
{
    return access(baseName(loc, accessOn),'.', 
        qNameAttrOccur(qName(loc, name), 
        location=loc), location=loc);
} 


function mkOrigin
Expr ::= loc::Location ns::NamedSignature
{
    return argFunc(loc,
        mkOriginName(ns.outputElement.typerep.typeName), 
        oneApp(loc, baseName(loc, ns.outputElement.elementName)));
}

-- This isn't here because this is difficult,
-- but so we are consistent whenver we create this name
function mkOriginName
String ::= typeName::String
{
    return "origin_" ++ typeName;
}

function oneApp
AppExprs ::= loc::Location e::Expr
{
    return oneAppExprs(presentAppExpr(e, location=loc), location=loc);
}

function argTransAttrs
AppExprs ::= loc::Location nsElems::[NamedSignatureElement] attr::String
{   
    -- Todo: (here and other places) what about elements without
    -- this attribute, esp. builtins?
    return if length(nsElems) == 1 
        then oneApp(loc, exprAccess(loc, attr, head(nsElems).elementName))
        else snocAppExprs(argTransAttrs(loc,tail(nsElems), attr), ',',
            presentAppExpr(exprAccess(loc, attr, head(nsElems).elementName),
        location=loc), location=loc);
}

function prdRecurse
Expr ::= loc::Location ns::NamedSignature tName::String
{
    return application(baseName(loc, ns.fullName), '(',
        argTransAttrs(loc, ns.inputElements, tName),
        ',',
        annoAppExprList([
            annExpr(loc, "labels", emptyList('[',']', location=loc)),
            -- This is not worked out completely
            -- We want to access the inhOrigin of the leftmost rhs element, if it has
            -- an inhOrigin, and otherwise top.inhOrigin
            annExpr(loc, "redex", exprAccess(loc, "inhOrigin_"++tName, inhOriginName(ns))),
            annExpr(loc, "origin", mkOrigin(loc, ns))
        ], loc),
        ')', location=loc);
}

function baseName
Expr ::= loc::Location name::String 
{
    return baseExpr(qName(loc,name), location=loc);
}

function presentName
AppExpr ::= loc::Location name::String
{
    return presentAppExpr(baseName(loc,name), location=loc);
}

function appExprList
AppExprs ::= aExprs::[AppExpr] loc::Location
{
    return if length(aExprs) == 1 
        then oneAppExprs(head(aExprs), location=loc)
        else snocAppExprs(
            appExprList(tail(aExprs), loc), ',', head(aExprs), location=loc);
}

function annoAppExprList
AnnoAppExprs ::= aaExprs::[AnnoExpr] loc::Location
{  
    return if length(aaExprs) == 1 
        then oneAnnoAppExprs(head(aaExprs), location=loc)
        else snocAnnoAppExprs(
            annoAppExprList(tail(aaExprs), loc), ',', head(aaExprs), location=loc);
}

function annExpr
AnnoExpr ::= loc::Location annoName::String e::Expr
{
    return annoExpr(qName(loc, annoName), '=', presentAppExpr(e,
        location=loc),location=loc);
}

function inhOriginName
String ::= ns::NamedSignature
{
    return if !null(ns.inputElements)
        then validInhOrigin(head(ns.inputNames), ns.outputElement.elementName)
        else ns.outputElement.elementName;
}

function validInhOrigin
String ::= test::String def::String
{
    -- For now just check against built ins
    return if test == "Integer" then def
        else if test == "String" then def
        -- else etc.
        else test;
}

function nsApply
Expr ::= loc::Location ns::NamedSignature
{
    return if null(ns.inputElements) 
        then emptyFunc(loc, ns.fullName)
        else argFunc(loc, ns.fullName, nsElemsToAppExprs(loc, ns.inputElements));
}

function nsElemsToAppExprs
AppExprs ::= loc::Location nsElems::[NamedSignatureElement]
{
    return if null(nsElems) then emptyAppExprs(location=loc)
        else snocAppExprs(
            nsElemsToAppExprs(loc, allHead(nsElems)),
            ',',
            nsElemToAppExpr(loc, last(nsElems)),
            location=loc); 
}

function nsElemToAppExpr
AppExpr ::= loc::Location nsElem::NamedSignatureElement
{
    return presentName(loc, nsElem.elementName);
}

function allHead
[a] ::= ls::[a]
{
    return if length(ls) == 1 then [] else head(ls) :: allHead(tail(ls));
}

function hasLocDcl
Boolean ::= dcl::[DclInfo]
{
    return if null(dcl) then false 
        else if head(dcl).isAnnotation && head(dcl).fullName == "location"
        then true
        else hasLocDcl(tail(dcl));
}

function hasNamedAttr
Boolean ::= tyName::String env::Decorated Env hasAttr::String
{
    return containsAttr(getAttrsOn(tyName, env), hasAttr);
}

function containsAttr
Boolean ::= dcl::[DclInfo] hasAttr::String
{   
    return if null(dcl) then false
        else if head(dcl).fullName == hasAttr
        then true
        else containsAttr(tail(dcl), hasAttr); 
}

function botlOneString
BracketedOptTypeExprs ::= loc::Location s::String
{
    return botlSome('<', 
        typeListSingle(sTyExpr(loc,s),location=loc),
        '>', location=loc);
}

function mkProdSig
ProductionSignature ::= loc::Location lhsName::String lhsType::String rhsName::String rhsType::String
{
    return productionSignature(
        productionLHS(name(lhsName, loc), '::', sTyExpr(loc, lhsType), location=loc),
        '::=',
        productionRHSCons(
            productionRHSElem(name(rhsName, loc), '::', sTyExpr(loc, rhsType), location=loc),
            productionRHSNil(location=loc), location=loc), location=loc);
}

function mkAspectProdSig
AspectProductionSignature ::= loc::Location lhsName::String lhsType::String rhsName::String rhsType::String
{
    return aspectProductionSignature(
        aspectProductionLHSTyped(name(lhsName, loc), '::', sTyExpr(loc, lhsType), location=loc),
        '::=',
        aspectRHSElemCons(
            aspectRHSElemTyped(name(rhsName, loc), '::', sTyExpr(loc, rhsType), location=loc),
            aspectRHSElemNil(location=loc), location=loc), location=loc);
}