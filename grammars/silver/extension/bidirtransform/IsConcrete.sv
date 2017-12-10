grammar silver:extension:bidirtransform;

inherited attribute isConcrete::Boolean occurs on NamedSignature, Def;

aspect production productionDcl
top::AGDcl ::= 'abstract' 'production' id::Name ns::ProductionSignature body::ProductionBody
{
    namedSig.isConcrete = false;
}

aspect production concreteProductionDcl
top::AGDcl ::= 'concrete' 'production' id::Name ns::ProductionSignature pm::ProductionModifiers body::ProductionBody
{
    namedSig.isConcrete = true;
}