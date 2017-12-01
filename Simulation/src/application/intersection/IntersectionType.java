package application.intersection;

/**
 * enumerates all the possible road direction combinations at an intersection
 * It seems that some of these would be pretty much useless, but it does list all the possibilities 
 * 
 * the numbers in the enum string indicate the direction that vehicles CAN travel AFTER going THROUGH the intersection
 * for example, a NSE intersection would look like:
 * 		| |_
 * 		|  _
 * 		| |
 * 
 * the same order of intersection points/integers referring to directions is used in this class:
 * (South, East, West, North)
 * 		|S|W| >> |0|2|
 * 		|E|N| >> |1|3|
 * 
 * @author Carolyn
 *
 */

public enum IntersectionType {
	//4-way intersection
	NSEW { 
		public boolean isOpen(int d) { return true; } //4-way open all directions
		public IntersectionType blockDirection(int d) { 
			if (d == 0) return NEW; 
			else if (d == 1) return NSW;
			else if (d == 2) return NSE; 
			else if (d == 3) return SEW;
			else return null; //this shouldn't happen ever
		}
	},
	//3-way intersections
	NSE { 
		public boolean isOpen(int d) { if (d == 2) return false; else return true; } 
		public IntersectionType blockDirection(int d) {
			if (d == 0) return NE;
			else if (d == 1) return NS;
			else if (d == 2) return NSE;	//west is already blocked
			else if (d == 3) return SE;
			else return null; //this shouldn't happen ever
		}
	},
	NSW { 
		public boolean isOpen(int d) { if (d == 1) return false; else return true; } 
		public IntersectionType blockDirection(int d) {
			if (d == 0) return NW;
			else if (d == 1) return NSW;	//east is already blocked
			else if (d == 2) return NS;
			else if (d == 3) return SW;
			else return null; //this shouldn't happen ever
		}
	},
	NEW { 
		public boolean isOpen(int d) { if (d == 0) return false; else return true; } 
		public IntersectionType blockDirection(int d) {
			if (d == 0) return NEW;			//south is already blocked
			else if (d == 1) return NW;
			else if (d == 2) return NE;
			else if (d == 3) return EW;
			else return null; //this shouldn't happen ever
		}
	},
	SEW { 
		public boolean isOpen(int d) { if (d == 3) return false; else return true; } 
		public IntersectionType blockDirection(int d) {
			if (d == 0) return EW;
			else if (d == 1) return SW;
			else if (d == 2) return SE;
			else if (d == 3) return SEW;	//north is already blocked
			else return null; //this shouldn't happen ever
		}
	},
	//2-way intersections
	NS { 
		public boolean isOpen(int d) { if (d == 1 || d == 2) return false; else return true; } 
		public IntersectionType blockDirection(int d) {
			if (d == 0) return N;
			else if (d == 1) return NS;		//east is already blocked
			else if (d == 2) return NS;		//west is already blocked
			else if (d == 3) return S;
			else return null; //this shouldn't happen ever
		}
	},
	NE { 
		public boolean isOpen(int d) { if (d == 0 || d == 2) return false; else return true; } 
		public IntersectionType blockDirection(int d) {
			if (d == 0) return NE;			//south is already blocked
			else if (d == 1) return N;
			else if (d == 2) return NE;		//west is already blocked
			else if (d == 3) return E;
			else return null; //this shouldn't happen ever
		}
	},
	NW { 
		public boolean isOpen(int d) { if (d == 0 || d == 1) return false; else return true; } 
		public IntersectionType blockDirection(int d) {
			if (d == 0) return NW;			//south is already blocked
			else if (d == 1) return NW;		//east is already blocked
			else if (d == 2) return N;
			else if (d == 3) return W;
			else return null; //this shouldn't happen ever
		}
	},
	SE { 
		public boolean isOpen(int d) { if (d == 2 || d == 3) return false; else return true; } 
		public IntersectionType blockDirection(int d) {
			if (d == 0) return E;
			else if (d == 1) return S;
			else if (d == 2) return SE;		//west is already blocked
			else if (d == 3) return SE;		//north is already blocked
			else return null; //this shouldn't happen ever
		}
	},
	SW { 
		public boolean isOpen(int d) { if (d == 1 || d == 3) return false; else return true; } 
		public IntersectionType blockDirection(int d) {
			if (d == 0) return W;
			else if (d == 1) return SW;		//east is already blocked
			else if (d == 2) return S;
			else if (d == 3) return SW;		//north is already blocked
			else return null; //this shouldn't happen ever
		}
	},
	EW { 
		public boolean isOpen(int d) { if (d == 0 || d == 3) return false; else return true; } 
		public IntersectionType blockDirection(int d) {
			if (d == 0) return EW;			//south is already blocked
			else if (d == 1) return W;
			else if (d == 2) return E;
			else if (d == 3) return EW;		//north is already blocked
			else return null; //this shouldn't happen ever
		}
	},
	//dead ends, I guess
	N { 
		public boolean isOpen(int d) { if (d != 3) return false; else return true; } 
		public IntersectionType blockDirection(int d) {
			if (d == 0) return N;			//south is already blocked
			else if (d == 1) return N;		//east is already blocked
			else if (d == 2) return N;		//west is already blocked
			else if (d == 3) return NONE;
			else return null; //this shouldn't happen ever
		}
	},
	S { 
		public boolean isOpen(int d) { if (d != 0) return false; else return true; } 
		public IntersectionType blockDirection(int d) {
			if (d == 0) return NONE;
			else if (d == 1) return S;		//east is already blocked
			else if (d == 2) return S;		//west is already blocked
			else if (d == 3) return S;		//north is already blocked
			else return null; //this shouldn't happen ever
		}
	},
	E { 
		public boolean isOpen(int d) { if (d != 1) return false; else return true; } 
		public IntersectionType blockDirection(int d) {
			if (d == 0) return E;			//south is already blocked
			else if (d == 1) return NONE;
			else if (d == 2) return E;		//west is already blocked
			else if (d == 3) return E;		//north is already blocked
			else return null; //this shouldn't happen ever
		}
	},
	W { 
		public boolean isOpen(int d) { if (d != 2) return false; else return true; } 
		public IntersectionType blockDirection(int d) {
			if (d == 0) return W;			//south is already blocked
			else if (d == 1) return W;		//east is already blocked
			else if (d == 2) return NONE;
			else if (d == 3) return W;		//north is already blocked
			else return null; //this shouldn't happen ever
		}
	},
	//non-existent or completely blocked intersection
	NONE {
		public boolean isOpen(int d) { return false; }	//none is closed all directions
		public IntersectionType blockDirection(int d) {
			if (d <=3 && d >=0) return NONE;	//all directions already blocked
			else return null; //this shouldn't happen ever
		}
	};
	
	public abstract boolean isOpen(int d);
	public abstract IntersectionType blockDirection(int d);
	
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
		else return null; //because this should never be the case... a no-direction intersection is not an intersection at all
	}
}
