import java.lang.Math;

public class Knapsack {
	/* 
	 * val wt | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |
	 *  1  1  | 0 | 1 | 1 | 1 | 1 | 1 | 1 | 1 |
	 *  4  3  | 0 | 1 | 1 | 4 | 5 | 5 | 5 | 5 |
	 *  5  4  | 0 | 1 | 1 | 4 | 5 | 6 | 6 | 9 |
	 *  7  5  | 0 | 1 | 1 | 4 | 5 | 7 | 8 | 9 |
	 */
	public static void main(String[] args) {
		// Assumptions: items are sorted by least to greatest weight
		int[] weights = {1, 3, 4, 5}; 
		int[] values = {1, 4, 5, 7};
		int weightCap = 7;
		
		/* Displaying the items we're working with */
		System.out.print("Weights: ");
		for (int w : weights) {
			System.out.print(w + " ");
		}
		System.out.println();
		System.out.print("Values: ");
		for (int v : values) {
			System.out.print(v + " ");
		}
		System.out.println();
		
		int[][] knapArr = findKnapArr(weights, values, weightCap);
//		printKnapArray(knapArr, values, weights); // Displays our filled array
		System.out.println("Your knapsack contains: ");
		fillSack(weights, values, weightCap, knapArr);
	}
	
	/* 
	 * findKnapArr
	 * - takes in the given items we're working with and the weight capacity
	 * - returns an array that shows the greatest possible value in every hypothetical situation (i.e. the best value at every weight at every item)
	 */
	public static int[][] findKnapArr(int[] weights, int[] values, int weightCap) {
		int items = weights.length;
		int[][] knapArr = new int[items][weightCap + 1]; 				// knapArr[a][b] --> the value of item 'a' of weight 'b'
		
		/* At the first item, for each weight that is >= to item's weight, the best value is the value of that one item. */
		for (int weight = 0; weight <= weightCap; weight++)
			if (weight >= weights[0]) knapArr[0][weight] = values[0];
			else knapArr[0][weight] = 0;
		
		for (int item = 1; item < items; item++) { 
			knapArr[item][0] = 0; 										// At a weight of 0, no matter what items I have, the sack should have NO items.
			for (int weight = 1; weight <= weightCap; weight++) {   	 	// For each hypothetical weight,
				if (weights[item] > weight) 								// If the item's weight is greater than our hypothetical weight,
					knapArr[item][weight] = knapArr[item-1][weight]; 		// then the value has to be the one above (b/c we know for sure that the above item's weight is less than the hypothetical weight) 
				else 													// If the item's weight is less, then compare the values between the item + remaining weight's value and the one above
					knapArr[item][weight] = Math.max(values[item] + knapArr[item-1][weight-weights[item]], knapArr[item-1][weight]); /* values[item-1] is NOT THE SAME AS knapArr[item-1][weight] * values[item-1] refers to an item's value (taken from the *list*). it does not refer to the value of the item above it. */
			}
		}
		return knapArr;
	}
	
	/*
	 * fillSack 
	 * - takes in the given items we're working with, the weight capacity, and the array returned by findKnapArr
	 * - displays the necessary items in our optimized knapsack
	 */
	public static void fillSack(int[] weights, int[] values, int weightCap, int[][] knapArr) {
		int items = weights.length-1; 						// Total number of items (zero-based value)
		int bestVal = knapArr[items][weightCap]; 			// The best possible value the knapsack can have (aka the bottom-right-most value)
		int tmpWeight = weightCap, tmpItem = items, tmpVal = bestVal;
		int[][] bestItems = new int[items+1][2];				// Array that contains the best knapsack's items 
		int i = 0;											// i is the position of the bestItems array
		while (tmpItem >= 1) {
			if (tmpVal != knapArr[tmpItem-1][tmpWeight]) {	// If the value doesn't come from the one above it,
				bestItems[i][0] = values[tmpItem];			// then the item at that row must have been used. 
				bestItems[i][1] = weights[tmpItem];			// Therefore, store that item's value and weight
				tmpWeight -= weights[tmpItem];				// Adjust the weight,
				i++;											// and we're ready to find the next item in bestItems
			}
			tmpItem--;
			tmpVal = knapArr[tmpItem][tmpWeight];
		}
		
		/* Displays the items in bestItems */
		for (int j = 0; j < bestItems.length; j++) {
			if (bestItems[j][0] != 0)
				System.out.println("Item of value: " + bestItems[j][0] + " and weight: " + bestItems[j][1]);
		}
	}
	
	/* 
	 * printKnapArray
	 * - takes in the array returned by findKnapArr, and the given items we're working with
	 */
	public static void printKnapArray(int[][] arr, int[] values, int[] weights) {
		for (int row = 0; row < arr.length; row++) {
			for (int col = 0; col < arr[row].length; col++) {
					System.out.print(" " + arr[row][col]);
			}
			System.out.println();
		}
	}

}
