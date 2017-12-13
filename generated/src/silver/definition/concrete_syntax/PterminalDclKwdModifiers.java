
package silver.definition.concrete_syntax;

// top::AGDcl ::= t::TerminalKeywordModifier 'terminal' id::Name r::RegExpr ';' 
public final class PterminalDclKwdModifiers extends silver.definition.core.NAGDcl {

	public static final int i_t = 0;
	public static final int i__G_3 = 1;
	public static final int i_id = 2;
	public static final int i_r = 3;
	public static final int i__G_0 = 4;


	public static final Class<?> childTypes[] = {silver.definition.concrete_syntax.NTerminalKeywordModifier.class,silver.definition.core.TTerminal_kwd.class,silver.definition.core.NName.class,silver.definition.concrete_syntax.NRegExpr.class,silver.definition.core.TSemi_t.class};

	public static final int num_local_attrs = Init.count_local__ON__silver_definition_concrete_syntax_terminalDclKwdModifiers;
	public static final String[] occurs_local = new String[num_local_attrs];

	public static final common.Lazy[] forwardInheritedAttributes = new common.Lazy[silver.definition.core.NAGDcl.num_inh_attrs];

	public static final common.Lazy[] synthesizedAttributes = new common.Lazy[silver.definition.core.NAGDcl.num_syn_attrs];
	public static final common.Lazy[][] childInheritedAttributes = new common.Lazy[5][];

	public static final common.Lazy[] localAttributes = new common.Lazy[num_local_attrs];
	public static final common.Lazy[][] localInheritedAttributes = new common.Lazy[num_local_attrs][];

	static {
	childInheritedAttributes[i_t] = new common.Lazy[silver.definition.concrete_syntax.NTerminalKeywordModifier.num_inh_attrs];
	childInheritedAttributes[i_id] = new common.Lazy[silver.definition.core.NName.num_inh_attrs];
	childInheritedAttributes[i_r] = new common.Lazy[silver.definition.concrete_syntax.NRegExpr.num_inh_attrs];

	}

	public PterminalDclKwdModifiers(final Object c_t, final Object c__G_3, final Object c_id, final Object c_r, final Object c__G_0, final Object a_core_location) {
		super(a_core_location);
		this.child_t = c_t;
		this.child__G_3 = c__G_3;
		this.child_id = c_id;
		this.child_r = c_r;
		this.child__G_0 = c__G_0;

	}

	private Object child_t;
	public final silver.definition.concrete_syntax.NTerminalKeywordModifier getChild_t() {
		return (silver.definition.concrete_syntax.NTerminalKeywordModifier) (child_t = common.Util.demand(child_t));
	}

	private Object child__G_3;
	public final silver.definition.core.TTerminal_kwd getChild__G_3() {
		return (silver.definition.core.TTerminal_kwd) (child__G_3 = common.Util.demand(child__G_3));
	}

	private Object child_id;
	public final silver.definition.core.NName getChild_id() {
		return (silver.definition.core.NName) (child_id = common.Util.demand(child_id));
	}

	private Object child_r;
	public final silver.definition.concrete_syntax.NRegExpr getChild_r() {
		return (silver.definition.concrete_syntax.NRegExpr) (child_r = common.Util.demand(child_r));
	}

	private Object child__G_0;
	public final silver.definition.core.TSemi_t getChild__G_0() {
		return (silver.definition.core.TSemi_t) (child__G_0 = common.Util.demand(child__G_0));
	}



	@Override
	public Object getChild(final int index) {
		switch(index) {
			case i_t: return getChild_t();
			case i__G_3: return getChild__G_3();
			case i_id: return getChild_id();
			case i_r: return getChild_r();
			case i__G_0: return getChild__G_0();

			default: return null;
		}
	}

	@Override
	public Object getChildLazy(final int index) {
		switch(index) {
			case i_t: return child_t;
			case i__G_3: return child__G_3;
			case i_id: return child_id;
			case i_r: return child_r;
			case i__G_0: return child__G_0;

			default: return null;
		}
	}

	@Override
	public final int getNumberOfChildren() {
		return 5;
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
		return ((silver.definition.core.NAGDcl)new silver.definition.concrete_syntax.PterminalDclDefault(common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.definition.concrete_syntax.PterminalDclKwdModifiers.i_t)), common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.definition.concrete_syntax.PterminalDclKwdModifiers.i_id)), common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.definition.concrete_syntax.PterminalDclKwdModifiers.i_r)), new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.concrete_syntax.NTerminalModifiers)new silver.definition.concrete_syntax.PterminalModifiersNone(((core.NLocation)((silver.definition.core.TSemi_t)context.childAsIs(silver.definition.concrete_syntax.PterminalDclKwdModifiers.i__G_0)).location))); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NLocation)((silver.definition.core.NAGDcl)context.undecorate()).getAnno_core_location()); } }));
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
		return "silver:definition:concrete_syntax:terminalDclKwdModifiers";
	}

	static void initProductionAttributeDefinitions() {

	}

	public static final common.NodeFactory<PterminalDclKwdModifiers> factory = new Factory();

	public static final class Factory extends common.NodeFactory<PterminalDclKwdModifiers> {

		@Override
		public PterminalDclKwdModifiers invoke(final Object[] children, final Object[] annotations) {
			return new PterminalDclKwdModifiers(children[0], children[1], children[2], children[3], children[4], annotations[0]);
		}
	};

}
