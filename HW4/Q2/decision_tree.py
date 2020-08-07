from util import entropy, information_gain, partition_classes, best_split
import numpy as np 
import ast

class DecisionTree(object):
    def __init__(self, max_depth):
        # Initializing the tree as an empty dictionary or list, as preferred
        self.tree = {}
        self.max_depth = max_depth
        pass
    	
    def learn(self, X, y, par_node = {}, depth=0):
        # TODO: Train the decision tree (self.tree) using the the sample X and labels y
        # You will have to make use of the functions in utils.py to train the tree

        # Use the function best_split in util.py to get the best split and 
        # data corresponding to left and right child nodes
        
        # One possible way of implementing the tree:
        #    Each node in self.tree could be in the form of a dictionary:
        #       https://docs.python.org/2/library/stdtypes.html#mapping-types-dict
        #    For example, a non-leaf node with two children can have a 'left' key and  a 
        #    'right' key. You can add more keys which might help in classification
        #    (eg. split attribute and split value)
        ### Implement your code here
        #############################################
        
        
        #if the node is leaf when all items in y are identical
        flag = 0
        first = y[0]
        for i in range(0, len(y)):
            if y[i] != first:
                flag = 1
        
        if flag == 0:
            self.tree['class'] = 'child'
            self.tree['label'] = first
            return
        
        
        split_attribute, split_value, X_left, X_right, y_left, y_right = best_split(X, y)
        
        
        self.tree['split_attribute'] = split_attribute
        self.tree['split_value'] = split_value
        self.tree['class'] = 'parent'
        self.tree['left'] = DecisionTree(self.max_depth)
        self.tree['right'] = DecisionTree(self.max_depth)
        
        par_node = self.tree
        
        self.tree['left'].learn(X_left, y_left, par_node, depth+1)
        self.tree['right'].learn(X_right, y_right, par_node, depth+1)
        
    
        #############################################


    def classify(self, record):
        # TODO: classify the record using self.tree and return the predicted label
        ### Implement your code here
        #############################################
        
        current_tree = self.tree
        
        while current_tree['class'] == 'parent': # not leaf
            if isinstance(record[current_tree['split_attribute']],str):
                if record[current_tree['split_attribute']] != current_tree['split_value']:
                    current_tree = current_tree['right'].tree
                else:
                    current_tree = current_tree['left'].tree
            else:
                if record[current_tree['split_attribute']] > current_tree['split_value']:
                    current_tree = current_tree['right'].tree
                else:
                    current_tree = current_tree['left'].tree

        return current_tree['label']
        
        #############################################
