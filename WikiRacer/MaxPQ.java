import java.util.List;

/*
 * TODO: This file should contain your priority queue backed by a binary
 * max-heap.
 */
public class MaxPQ {
	
	private final int DEFAULT_CAPACITY = 10;
	RankedList[] ladderqueue;
	private int size;
	
	public MaxPQ() {
		ladderqueue = new RankedList[DEFAULT_CAPACITY];
		size = 0;
	}
	
	   private void growArray() {
	        RankedList[] newArray = new RankedList[ladderqueue.length * 2];
	        for (int i = 1; i < this.size + 1; i++) {
	            newArray[i] = ladderqueue[i];
	        }
	        this.ladderqueue = newArray;
	    }
	    
		private boolean needsToGrow() {
			int endIndex = size + 1;
			if (((endIndex * 2) + 1) > ladderqueue.length) {
				return true;
			} else {
				return false;
			}
		}
		

		public void enqueue(List<String> ladder, int priority) {
			RankedList newRankedList = new RankedList(ladder, priority);
			if (this.needsToGrow()) {
				this.growArray();
			} 
			int endIndex = size + 1;
			ladderqueue[endIndex] = newRankedList;
			bubbleDown(newRankedList, endIndex);
			size++;
		}
		

		public List<String> dequeue() throws ArrayIndexOutOfBoundsException  {
			if (this.isEmpty()) throw new ArrayIndexOutOfBoundsException();
			
			RankedList storeRankedList = ladderqueue[1];
			ladderqueue[1] = ladderqueue[size];
			ladderqueue[size] = storeRankedList;
			
			RankedList returnRankedList = ladderqueue[size];
			ladderqueue[size] = null;
			
			bubbleUp(ladderqueue[1], 1);
			
			size--;
			return returnRankedList.listRep;
		}
		

		private void bubbleUp(RankedList ladder, int index) {
			int mostUrgentChildIndex = mostUrgentChildIndex(ladder, index);
			boolean inRightPlace = mostUrgentChildIndex == -1;
			
			while (!inRightPlace) {
				RankedList mostUrgentChild = ladderqueue[mostUrgentChildIndex];
				ladderqueue[index] = mostUrgentChild;
				ladderqueue[mostUrgentChildIndex] = ladder;
				
				index = mostUrgentChildIndex;
				ladder = ladderqueue[mostUrgentChildIndex];
				mostUrgentChildIndex = mostUrgentChildIndex(ladder, index);
				inRightPlace = mostUrgentChildIndex == -1;
			}
		}
		
		private int mostUrgentChildIndex(RankedList ladder, int index) {
			int indexChildOne = index * 2;
			int indexChildTwo = (index * 2) + 1;
			RankedList childOne = ladderqueue[indexChildOne];
			RankedList childTwo = ladderqueue[indexChildTwo];
			
			boolean bothNull = childOne == null && 
					childTwo == null;
			if (bothNull) return -1;
			
			if (childOne == null) {
				if (childTwo.priority == ladder.priority) {
					return -1;
				 } else if (childTwo.priority < ladder.priority) {
					return -1;
				 } else {
					return indexChildTwo;
				 }
				
			} else if (childTwo == null) {
				if (childOne.priority == ladder.priority) {
					return -1;
				} else if (childOne.priority < ladder.priority) {
					return -1;
				} else {
					return indexChildOne;
				} 
			} else {
				boolean bothChildrenSamePrio = childOne.priority == ladder.priority &&
						childTwo.priority == ladder.priority;
				if (bothChildrenSamePrio) {
					return -1;
				}
				int biggerPrio =  Math.max(childOne.priority, childTwo.priority);
				if (biggerPrio == childOne.priority) return indexChildOne;
				else return indexChildTwo;
				}

			} 
		

		private void bubbleDown(RankedList ladder, int index) {
			RankedList parent = ladderqueue[index / 2];
			boolean inRightPlace;
			if (parent == null) inRightPlace = true;
			else {
				if (parent.priority == ladder.priority) {
					inRightPlace = true;
				} else {
					inRightPlace = parent.priority > ladder.priority;
				}

			}

			while (!inRightPlace) {
				int parentIndex = index / 2;
				ladderqueue[index] = parent;
				ladderqueue[parentIndex] = ladder;
				
				index = parentIndex;
				parent = ladderqueue[index / 2];
				if (parent == null) inRightPlace = true;
				else {
					if (parent.priority == ladder.priority) {
						inRightPlace = true;
					} else {
						inRightPlace = parent.priority > ladder.priority;
					}
				}
			}
		}
		
		public String toString() {

			String result = "{";
			if (!this.isEmpty()) {
				for (int i = 1; i < size; i ++) {
					result += ladderqueue[i].toString() + ", ";
				}
				result += ladderqueue[size].toString() + "}";
				
				return result;
			}
			else {
				return result + "}";
			}
		}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	private class RankedList {
		
		private List<String> listRep;
		private int priority;
		
		public RankedList(List<String> listRep, int priority) {
			this.listRep = listRep;
			this.priority = priority;
		}
		
		public String toString() {
			return listRep.toString() + ", " + priority;
		}
	}
	
}
	
	
