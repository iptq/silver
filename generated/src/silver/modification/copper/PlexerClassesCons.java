
package silver.modification.copper;

// top::ClassList ::= n::QName ',' cl_tail::ClassList 
public final class PlexerClassesCons extends silver.modification.copper.NClassList {

	public static final int i_n = 0;
	public static final int i__G_1 = 1;
	public static final int i_cl_tail = 2;


	public static final Class<?> childTypes[] = {silver.definition.core.NQName.class,silver.definition.core.TComma_t.class,silver.modification.copper.NClassList.class};

	public static final int num_local_attrs = Init.count_local__ON__silver_modification_copper_lexerClassesCons;
	public static final String[] occurs_local = new String[num_local_attrs];

	public static final common.Lazy[] forwardInheritedAttributes = new common.Lazy[silver.modification.copper.NClassList.num_inh_attrs];

	public static final common.Lazy[] synthesizedAttributes = new common.Lazy[silver.modification.copper.NClassList.num_syn_attrs];
	public static final common.Lazy[][] childInheritedAttributes = new common.Lazy[3][];

	public static final common.Lazy[] localAttributes = new common.Lazy[num_local_attrs];
	public static final common.Lazy[][] localInheritedAttributes = new common.Lazy[num_local_attrs][];

	static {
	childInheritedAttributes[i_n] = new common.Lazy[silver.definition.core.NQName.num_inh_attrs];
	childInheritedAttributes[i_cl_tail] = new common.Lazy[silver.modification.copper.NClassList.num_inh_attrs];

	}

	public PlexerClassesCons(final Object c_n, final Object c__G_1, final Object c_cl_tail, final Object a_core_location) {
		super(a_core_location);
		this.child_n = c_n;
		this.child__G_1 = c__G_1;
		this.child_cl_tail = c_cl_tail;

	}

	private Object child_n;
	public final silver.definition.core.NQName getChild_n() {
		return (silver.definition.core.NQName) (child_n = common.Util.demand(child_n));
	}

	private Object child__G_1;
	public final silver.definition.core.TComma_t getChild__G_1() {
		return (silver.definition.core.TComma_t) (child__G_1 = common.Util.demand(child__G_1));
	}

	private Object child_cl_tail;
	public final silver.modification.copper.NClassList getChild_cl_tail() {
		return (silver.modification.copper.NClassList) (child_cl_tail = common.Util.demand(child_cl_tail));
	}



	@Override
	public Object getChild(final int index) {
		switch(index) {
			case i_n: return getChild_n();
			case i__G_1: return getChild__G_1();
			case i_cl_tail: return getChild_cl_tail();

			default: return null;
		}
	}

	@Override
	public Object getChildLazy(final int index) {
		switch(index) {
			case i_n: return child_n;
			case i__G_1: return child__G_1;
			case i_cl_tail: return child_cl_tail;

			default: return null;
		}
	}

	@Override
	public final int getNumberOfChildren() {
		return 3;
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
		return true;
	}

	@Override
	public common.Node evalForward(final common.DecoratedNode context) {
		return ((silver.modification.copper.NClassList)new silver.modification.copper.PlexerClassesMain(common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.modification.copper.PlexerClassesCons.i_n)), common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.modification.copper.PlexerClassesCons.i_cl_tail)), new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NLocation)((silver.modification.copper.NClassList)context.undecorate()).getAnno_core_location()); } }));
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
		return "silver:modification:copper:lexerClassesCons";
	}

	static void initProductionAttributeDefinitions() {

	}

	public static final common.NodeFactory<PlexerClassesCons> factory = new Factory();

	public static final class Factory extends common.NodeFactory<PlexerClassesCons> {

		@Override
		public PlexerClassesCons invoke(final Object[] children, final Object[] annotations) {
			return new PlexerClassesCons(children[0], children[1], children[2], annotations[0]);
		}
	};

}