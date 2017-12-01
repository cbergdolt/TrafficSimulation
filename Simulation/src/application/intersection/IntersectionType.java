package application.intersection;

/**
 * enumerates all the possible road direction combinations at an intersection
 * It seems that some of these would be pretty much useless, but it does list all the possibilities 
 * 
 * @author Carolyn
 *
 */

public enum IntersectionType {
	NSEW,	//4-way intersection
	NSE,	//3-way intersections
	NSW,
	NEW,
	SEW,
	NS,		//2-way intersections
	NE,
	NW,
	SE,
	SW,
	EW,
	N,		//dead ends
	S,
	E,
	W;
	
	public IntersectionType getIntersectionType(boolean N, boolean S, boolean E, boolean W) {
		if (N && S && E && W) return NSEW;
		else if (N && S && E && !W) return NSE;
		else if (N && S && W && !E) return NSW;
		else if (N && E && W && !S) return NEW;
		else if (S && E && W && !N) return SEW;
		else if (N && S && !E && !W) return NS;
		else if (N && !S && E && !W) return NE;
		else if (N && !S && !E && W) return NW;
		else if (!N && S && E && !W) return SE;
		else if (!N && S && !E && W) return SW;
		else if (!N && !S && E && W) return EW;
		else if (N && !S && !E && !W) return IntersectionType.N;
		else if (!N && S && !E && !W) return IntersectionType.S;
		else if (!N && !S && E && !W) return IntersectionType.E;
		else if (!N && !S && !E && W) return IntersectionType.W;
		else return null; //because this should never be the case...
	}
}
