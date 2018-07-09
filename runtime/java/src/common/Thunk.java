package common;

/**
 * A thunk that ensures an expression is evaluated once, and memoizes the result.
 * 
 * <p>Thunks represent suspended computations that may be demanded later on (or never.)
 * 
 * @author tedinski
 */
public class Thunk<T> {
	
	// Instances should never escape this class
	public static interface Evaluable<T> {
		public T eval();
	}
	
	// Either T or Evaluable<T>
	private Object o;

	public Thunk(final Evaluable<T> e) {
		assert(e != null);
		o = e;
	}
	
	public T eval() {
		if(o instanceof Evaluable) {
			o = ((Evaluable<T>)o).eval();
			assert(o != null);
		}
		return (T)o;
	}
	
	public static Thunk<Object> fromLazy(Lazy l, DecoratedNode ctx) {
		return new Thunk(() -> l.eval(ctx));
	}
	
	/**
	 * Either evaluate e, or wrap it in a Thunk.  Common idiom when whether we want to be
	 * lazy is determined by a boolean flag.
	 * 
	 * @param e  An Evaluable to evaluate or wrap in a Thunk
	 * @param lazy  True if e should be wrapped in a Thunk
	 * @return Either a Thunk wrapping e or the result of evaluating e
	 */
	public static Object maybeLazy(final Evaluable<?> e, final boolean lazy) {
		return lazy? new Thunk(e) : e.eval();
	}
	
	/**
	 * Take a Thunk evaluating to a DecoratedNode, and undecorates it.
	 * 
	 * @param t  Either a DecoratedNode or a Thunk<DecoratedNode>
	 * @return  Either a Node or a Thunk<Node>
	 */
	public static Object transformUndecorate(final Object t) {
		// DecoratedNode
		if(t instanceof DecoratedNode)
			return ((DecoratedNode)t).undecorate();
		// Thunk
		return transformUndecorateThunk((Thunk<DecoratedNode>)t);
	}
	private static Object transformUndecorateThunk(final Thunk<DecoratedNode> t) {
		// Unevaluated Thunk
		if(t.o instanceof Evaluable)
			return new Thunk(() -> t.eval().undecorate());
		// Evaluated Thunk, eagerly undecorate:
		return t.eval().undecorate();
	}
}
