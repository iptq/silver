grammar silver:modification:let_fix:java;
import silver:modification:let_fix;
import silver:translation:java:core;
import silver:definition:core;
import silver:util;
import silver:definition:env;
import silver:translation:java:type;
import silver:definition:type;
import silver:definition:type:syntax;

-- TODO: this is an area where we're creating Lazys in a nested fashion.
-- (i.e. at execution, rather than initialization, continuously)
-- Ideally, we wouldn't be doing this...

aspect production letp
top::Expr ::= 'let' la::LetAssigns 'in' e::Expr 'end'
{
  top.translation = "((" ++ top.typerep.transType ++ ")common.Util.let(context, new String[]{" ++ implode(", ", la.nameTrans) ++ "}, " ++ 
					     "new common.Lazy[]{" ++ implode(", ", la.valueTrans) ++ "}, " ++ wrapLazy(forward) ++ "))"; 
}

attribute nameTrans, valueTrans occurs on LetAssigns, AssignExpr;

aspect production assigns
top::LetAssigns ::= ae::AssignExpr ',' list::LetAssigns
{
  top.nameTrans = ae.nameTrans ++ list.nameTrans;
  top.valueTrans = ae.valueTrans ++ list.valueTrans;
}

aspect production assignListSingle 
top::LetAssigns ::= ae::AssignExpr
{
  top.nameTrans = ae.nameTrans;
  top.valueTrans = ae.valueTrans;
}

aspect production assignExpr
top::AssignExpr ::= id::Name '::' t::Type '=' e::Expr
{
  top.nameTrans = ["\"" ++ fName ++ "\""];
  top.valueTrans = [wrapLazy(e)];
}

aspect production lexicalLocalReference
top::Expr ::= q::Decorated QName
{
  top.isAppReference = false;
  top.appReference = "";
  top.translation = if q.lookupValue.typerep.isDecorated && shouldUnDec
                    then "((" ++ finalType(top).transType ++ ")((common.DecoratedNode)context.lexical(\"" ++ q.lookupValue.fullName ++ "\")).undecorate())"
                    else "((" ++ finalType(top).transType ++ ")context.lexical(\"" ++ q.lookupValue.fullName ++ "\"))";
}

