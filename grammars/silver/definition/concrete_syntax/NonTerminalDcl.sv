grammar silver:definition:concrete_syntax;

aspect production nonterminalDcl
top::AGDcl ::= 'nonterminal' id::Name botl::BracketedOptTypeList ';'
{
  top.syntaxAst = [syntaxNonterminal(nonterminalTypeExp(fName, tl.types), nilSyntax())];  
}


