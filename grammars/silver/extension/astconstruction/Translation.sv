grammar silver:extension:astconstruction;

imports silver:reflect;
imports silver:hostEmbedding;

aspect production nonterminalAST
top::AST ::= prodName::String children::ASTs annotations::NamedASTs
{
  directEscapeProductions <-
    ["silver:extension:astconstruction:escapeAST"];
  varPatternProductions <-
    ["silver:extension:astconstruction:varAST"];
  wildPatternProductions <-
    ["silver:extension:astconstruction:wildAST"];
}