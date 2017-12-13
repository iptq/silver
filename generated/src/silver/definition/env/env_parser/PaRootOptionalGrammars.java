
package silver.definition.env.env_parser;

// top::IRootPart ::= 'optionalGrammars' i::INames 
public final class PaRootOptionalGrammars extends silver.definition.env.env_parser.NIRootPart {

	public static final int i__G_1 = 0;
	public static final int i_i = 1;


	public static final Class<?> childTypes[] = {silver.definition.env.env_parser.TOptionalGrammarsTerm.class,silver.definition.env.env_parser.NINames.class};

	public static final int num_local_attrs = Init.count_local__ON__silver_definition_env_env_parser_aRootOptionalGrammars;
	public static final String[] occurs_local = new String[num_local_attrs];

	public static final common.Lazy[] forwardInheritedAttributes = new common.Lazy[silver.definition.env.env_parser.NIRootPart.num_inh_attrs];

	public static final common.Lazy[] synthesizedAttributes = new common.Lazy[silver.definition.env.env_parser.NIRootPart.num_syn_attrs];
	public static final common.Lazy[][] childInheritedAttributes = new common.Lazy[2][];

	public static final common.Lazy[] localAttributes = new common.Lazy[num_local_attrs];
	public static final common.Lazy[][] localInheritedAttributes = new common.Lazy[num_local_attrs][];

	static {
	childInheritedAttributes[i_i] = new common.Lazy[silver.definition.env.env_parser.NINames.num_inh_attrs];

	}

	public PaRootOptionalGrammars(final Object c__G_1, final Object c_i) {
		super();
		this.child__G_1 = c__G_1;
		this.child_i = c_i;

	}

	private Object child__G_1;
	public final silver.definition.env.env_parser.TOptionalGrammarsTerm getChild__G_1() {
		return (silver.definition.env.env_parser.TOptionalGrammarsTerm) (child__G_1 = common.Util.demand(child__G_1));
	}

	private Object child_i;
	public final silver.definition.env.env_parser.NINames getChild_i() {
		return (silver.definition.env.env_parser.NINames) (child_i = common.Util.demand(child_i));
	}



	@Override
	public Object getChild(final int index) {
		switch(index) {
			case i__G_1: return getChild__G_1();
			case i_i: return getChild_i();

			default: return null;
		}
	}

	@Override
	public Object getChildLazy(final int index) {
		switch(index) {
			case i__G_1: return child__G_1;
			case i_i: return child_i;

			default: return null;
		}
	}

	@Override
	public final int getNumberOfChildren() {
		return 2;
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
		throw new common.exceptions.SilverInternalError("Production silver:definition:env:env_parser:aRootOptionalGrammars erroneously claimed to forward");
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
		return "silver:definition:env:env_parser:aRootOptionalGrammars";
	}

	static void initProductionAttributeDefinitions() {
		// top.optionalGrammars = i.names
		silver.definition.env.env_parser.PaRootOptionalGrammars.synthesizedAttributes[silver.definition.env.env_parser.Init.silver_definition_env_optionalGrammars__ON__silver_definition_env_env_parser_IRootPart] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return ((common.ConsCell)context.childDecorated(silver.definition.env.env_parser.PaRootOptionalGrammars.i_i).synthesized(silver.definition.env.env_parser.Init.silver_definition_env_env_parser_names__ON__silver_definition_env_env_parser_INames)); } };

	}

	public static final common.NodeFactory<PaRootOptionalGrammars> factory = new Factory();

	public static final class Factory extends common.NodeFactory<PaRootOptionalGrammars> {

		@Override
		public PaRootOptionalGrammars invoke(final Object[] children, final Object[] annotations) {
			return new PaRootOptionalGrammars(children[0], children[1]);
		}
	};

}
