grammar core:originsimpl;

function sexprify
String ::= nt::a
{
  return "<not implemented>";
}

function javaGetOrigin
Maybe<OriginInfo> ::= arg::a
{
  return nothing();
}

function javaGetOriginLink
Maybe<a> ::= arg::OriginInfo
{
  return nothing();
}