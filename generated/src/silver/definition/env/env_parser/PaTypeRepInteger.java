
package silver.definition.env.env_parser;

// top::ITypeRep ::= 'int' 
public final class PaTypeRepInteger extends silver.definition.env.env_parser.NITypeRep {

	public static final int i__G_0 = 0;


	public static final Class<?> childTypes[] = {silver.definition.env.env_parser.TIntegerTerm.class};

	public static final int num_local_attrs = Init.count_local__ON__silver_definition_env_env_parser_aTypeRepInteger;
	public static final String[] occurs_local = new String[num_local_attrs];

	public static final common.Lazy[] forwardInheritedAttributes = new common.Lazy[silver.definition.env.env_parser.NITypeRep.num_inh_attrs];

	public static final common.Lazy[] synthesizedAttributes = new common.Lazy[silver.definition.env.env_parser.NITypeRep.num_syn_attrs];
	public static final common.Lazy[][] childInheritedAttributes = new common.Lazy[1][];

	public static final common.Lazy[] localAttributes = new common.Lazy[num_local_attrs];
	public static final common.Lazy[][] localInheritedAttributes = new common.Lazy[num_local_attrs][];

	static {

	}

	public PaTypeRepInteger(final Object c__G_0) {
		super();
		this.child__G_0 = c__G_0;

	}

	private Object child__G_0;
	public final silver.definition.env.env_parser.TIntegerTerm getChild__G_0() {
		return (silver.definition.env.env_parser.TIntegerTerm) (child__G_0 = common.Util.demand(child__G_0));
	}



	@Override
	public Object getChild(final int index) {
		switch(index) {
			case i__G_0: return getChild__G_0();

			default: return null;
		}
	}

	@Override
	public Object getChildLazy(final int index) {
		switch(index) {
			case i__G_0: return child__G_0;

			default: return null;
		}
	}

	@Override
	public final int getNumberOfChildren() {
		return 1;
	}

	@Override
	public common.Lazy getSynthesized(final int index) {
		return synthesizedAttributes[index];
	}

	@Override
	public common.Lazy[] getLocalInheritedAttributes(final int key) {
		return localInheritedAttributes[key];
	}

	@Override
	public common.Lazy[] getChildInheritedAttributes(final int key) {
		return childInheritedAttributes[key];
	}

	@Override
	public boolean hasForward() {
		return false;
	}

	@Override
	public common.Node evalForward(final common.DecoratedNode context) {
		throw new common.exceptions.SilverInternalError("Production silver:definition:env:env_parser:aTypeRepInteger erroneously claimed to forward");
	}

	@Override
	public common.Lazy getForwardInheritedAttributes(final int index) {
		return forwardInheritedAttributes[index];
	}

	@Override
	public common.Lazy getLocal(final int key) {
		return localAttributes[key];
	}

	@Override
	public final int getNumberOfLocalAttrs() {
		return num_local_attrs;
	}

	@Override
	public final String getNameOfLocalAttr(final int index) {
		return occurs_local[index];
	}

	@Override
	public String getName() {
		return "silver:definition:env:env_parser:aTypeRepInteger";
	}

	static void initProductionAttributeDefinitions() {
		// top.typerep = intType()
		silver.definition.env.env_parser.PaTypeRepInteger.synthesizedAttributes[silver.definition.env.env_parser.Init.silver_definition_env_typerep__ON__silver_definition_env_env_parser_ITypeRep] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return ((silver.definition.type.NType)new silver.definition.type.PintType()); } };

	}

	public static final common.NodeFactory<PaTypeRepInteger> factory = new Factory();

	public static final class Factory extends common.NodeFactory<PaTypeRepInteger> {

		@Override
		public PaTypeRepInteger invoke(final Object[] children, final Object[] annotations) {
			return new PaTypeRepInteger(children[0]);
		}
	};

}