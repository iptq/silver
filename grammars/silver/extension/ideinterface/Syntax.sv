grammar silver:extension:ideinterface;


nonterminal IDEInterfaceSyntax;
nonterminal IDEInterfaceSyntaxDcl;

synthesized attribute ideSyntax :: IDEInterfaceSyntax occurs on Syntax;
synthesized attribute ideSyntaxDcl :: IDEInterfaceSyntaxDcl occurs on SyntaxDcl;

abstract production nilIDESyntax
top::IDEInterfaceSyntax ::=
{
}

aspect production nilSyntax
top::Syntax ::=
{
  top.ideSyntax = nilIDESyntax();
}

abstract production consIDESyntax
top::IDEInterfaceSyntax ::= dcl::IDEInterfaceSyntaxDcl rest::IDEInterfaceSyntax
{
}

aspect production consSyntax
top::Syntax ::= s1::SyntaxDcl s2::Syntax
{
  top.ideSyntax = 
  case s1 of 
  | syntaxTerminal(_, _, _) -> consIDESyntax(s1.ideSyntaxDcl, s2.ideSyntax)
  | syntaxNonterminal(_, _) -> consIDESyntax(s1.ideSyntaxDcl, s2.ideSyntax)
  | syntaxLexerClass(_, _)  -> consIDESyntax(s1.ideSyntaxDcl, s2.ideSyntax)
  | _ -> s2.ideSyntax
  end;
}

abstract production ideSyntaxTerminal
top::IDEInterfaceSyntaxDcl ::= name::String properties::IDEInterfaceTerminalProperties
{

}

aspect production syntaxTerminal
top::SyntaxDcl ::= name::String regex::Regex modifiers::SyntaxTerminalModifiers
{
  top.ideSyntaxDcl = ideSyntaxTerminal(name, syntaxTerminalModifiersToIDEProperties(modifiers));
}

abstract production ideSyntaxNonterminal
top::IDEInterfaceSyntaxDcl ::= name::String subdcls::IDEInterfaceSyntax
{
  
}

aspect production syntaxNonterminal
top::SyntaxDcl ::= t::Type subdcls::Syntax
{
  top.ideSyntaxDcl = ideSyntaxNonterminal(t.typeName, subdcls.ideSyntax);
}

abstract production ideSyntaxLexerClass
top::IDEInterfaceSyntaxDcl ::= name::String terms::[String]
{
}

aspect production syntaxLexerClass
top::SyntaxDcl ::= n::String modifiers::SyntaxLexerClassModifiers
{
  production terms :: [String] = searchEnvTree(modifiers.className, modifiers.classTerminals);
  top.ideSyntaxDcl = ideSyntaxLexerClass(n, terms);
}
