grammar silver:extension:treesitter;

imports silver:definition:env;
imports silver:definition:core;
imports silver:definition:concrete_syntax;
imports silver:definition:concrete_syntax:ast;
imports silver:definition:regex;

{--
 - The name of the language specified by this Tree-sitter grammar.
 -}
inherited attribute lang :: String occurs on SyntaxRoot;

{--
 - Translation of a CST AST to Tree-sitter Javascript.
 -}
synthesized attribute jsTreesitter :: String occurs on SyntaxRoot;

-- TODO: Why is SyntaxRoot closed?  Needs a default.
aspect default production
top::SyntaxRoot ::=
{
  top.jsTreesitter = error("Shouldn't happen");
}

aspect production cstRoot
top::SyntaxRoot ::= parsername::String  startnt::String  s::Syntax  terminalPrefixes::[Pair<String String>]
{
-- TODO: Generate the production rules of the context-free grammar
  top.jsTreesitter =
s"""module.exports = grammar({                                                      
    name: 'BinaryTree',                                                           
                                                                                  
    rules: {                                                                      
      LTree: $$ => choice(                                                         
        seq($$.lfork, $$.LTree, $$.RTree),                                           
        $$.lleaf                                                                   
      ),                                                                          
      RTree: $$ => choice(                                                         
        seq($$.rfork, $$.LTree, $$.RTree),                                           
        $$.rleaf                                                                   
      ),                                                                          
      lfork: $$ => 'lfork',                                                        
      rfork: $$ => 'rfork',                                                        
      lleaf: $$ => 'lleaf',                                                        
      rleaf: $$ => 'rleaf',                                                        
                                                                                  
    }                                                                             
  });""";
}