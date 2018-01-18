package files;

//class for LinkedLists of categories
    public class LinkedListCat
    {
    
      private Node head; //"head" - data type: Node
      public int listCount; //"listCount" - data type: int, acts like an index
      //it indicates the size of the list
   
       public LinkedListCat() //LinkedList Constructor - function name the same as class name
      {
         head = new Node(null); //The "head" variable is null
         listCount = 0; //The "listcount" is zero
      }
   
       public void add(category data)
      {
   ///////// THE FOLLOWING CODE TRAVERSES THE LIST ////////////
         Node temp = new Node(data); //New Node called 'temp' is a node that has the data from parameters
         Node current = head;// new variable called 'current' with data type node that is set to the head

         
         while(current.getNext() != null)//while the node next from the current node isn't "null"
         {
        	 
        	 //if we wanted to printout the data stored in each node in a linked list
        	 //System.out.println(current.info + " ");
            current = current.getNext(); //the 'current' variable will be equal to the next node
         }
         current.setNext(temp);    //the current node's next link is changed to temp
         //the construction of the Node class will already set the next to null
         //therefore you don't have to set the next of the temp node to null
         listCount++;//the listcount will + 1 each time        
         }
   
       public void add(category data, int index)
       //adds a node in a certain position of the linkedlist
      {
         Node temp = new Node(data);
         //New node called 'temp' is a node with the data from the parameters of the function
         
         Node current = head; 
         //the current node points to the 'head' node
         for(int i = 1; i < index && current.getNext() != null; i++) 
        	//for loop that will traverse the list until 'i' reaches the index wanted
        	 //also conditional by that the node next to the current node isn't null
         {
            current = current.getNext(); 
            //the current node will be equal to the node next to the current node
         }
         //Got to the desired position
         temp.setNext(current.getNext());
         //the temp node's next link is changed to the current's next node
         //this is because normally, in the Node class constructor, the node's setnext will be set to null
         current.setNext(temp);
         //the current node's set next link will be pointed to the 'temp' node
      
         listCount++; //adds 1 to the list count as 1 more node has been added
         }
   
       public category get(int index)//function with return type 'Object'
      {
         if(index <= 0)
        	 //if the index inquired is less than or equal to 0, then
            return null;
         //return a value of null
      
         Node current = head.getNext();
         //new Node called 'current' which is set to the node next to the head
         for(int i = 1; i < index; i++)
         {
        	 //this for loop traverses the list until the index inquired
            if(current.getNext() == null)
            	//if the Node next to the current is null
               return null;
            //return null
         
            current = current.getNext();
            //the current node will be equal to the node next to the current
            //[traverses the list]
         }
         return current.getData();
         //returns the current node's data
      }
   
       public boolean remove(int index)
       //remove function, parameter of index
       //return type of boolean
      {
         if(index < 1 || index > size())
        	 //if index is less than 1 or the index is more than the size of the linked list
            return false;
         //return false, as it is impossible to remove
      
         Node current = head;
         //declares new Node called 'current' that is set to the head of the linked list
         for(int i = 1; i < index; i++)
         {
        	 //for loop that traverses the list until the index number required
            if(current.getNext() == null)
            	//if current's next node is null, which mean that it's at the end of the list
               return false;
            //returns false
         
            current = current.getNext();
            //current is equal to the current's next node
         }
         current.setNext(current.getNext().getNext());
         //when the index has been reached, the current node's link to the next node is equal to the
         //node next to the next node (skips the next node)
         //this deletes the node in front by just removing it from the linked list
         listCount--;   
         //list count - 1 as one of the nodes is removed
         return true;
      }
   
       public int size()
       //function called 'size' with return type int
      {
         return listCount;
         //returns the value of the listCount variable
      }
   
       public String toString()
       //function toString with return type string
      {
         Node current = head.getNext();
         //New Node called 'current' which is set to the node next to the head node
         String output = "";
         //declares new string variable called 'output'
         while(current != null)
        	 //while current isn't null
         {
            output += "[" + current.getData().toString() + "]";
            //the output will be equal to output added with the data toString of the current node
            current = current.getNext();
            //the current node is equal to the current's next node
         }
         return output;
         //returns this output string
      }
   
       public class Node
      {
         Node next;
         //next is the data member of type node that will point to the next element
         category data;
         //data is the object of the data stored
      
          public Node(category _data) //a constructor [function has same name as the class]
         //initializes new object
         //the constructor takes the parameter of Object _data
         {
            next = null;
            //points to the end [null]
            data = _data;
            //assigns the parameter of the constructor to the 'data' member of the class
         }
      
          public Node(category _data, Node _next) //constructor that takes 2 parameters from the user
          //_data holds the data
          //_next holds the pointer to the next node
         {
            next = _next; //'next' member now points to the next node
            data = _data; //'data' member now holds the data of this node
         }
      
          public category getData() //query - returns data
          //function getData()
         {
            return data; //returns the data stored in the node as type Object
         }
      
          public void setData(category _data) //function setData() that takes in parameter of _data
         //data type of parameter Object
         //mutator that changes the data member
         {
            data = _data; //the 'data' variable now holds the data from _data
         }
      
          public Node getNext() //query to return the next Node
         {
            return next; //returns the 'next' member in data type Node
         }
      
          public void setNext(Node _next) //function setNext()
          //mutator that changes 'next' member
         {
            next = _next; //the 'next' member holds the reference to the next node
            //it will hold the node that has been entered in the parameter
         }
      }
   }
