public class LinkedList <T>
{
    int size = 0; //linked list ka size

    Node head = null;

    //insert value in ll
    public void Insert(T val)
    {
        //node creation
        Node temp = new Node<T>();
        temp.value = val;
        temp.next = null;

        //first node
        if(head == null)
            head = temp;
        else
        {
            //list already exists
            Node currN = head;
            //traversal
            while(currN.next != null)
                currN = currN.next;
            //node connected at end of existing list
            currN.next = temp;
        }
        size++; //size of linked list incremented

    } //end of insert function

    //return value at index
    public T get(int index)
    {
        Node currN = head;
        int i =0; //loop counter

        while(i!= index)
        {
            currN = currN.next;
            i++;
        }
        return (T) currN.value;
    }

    //display lll
    public void Display()
    {
        Node currN = head;

        // print traversal
        while(currN != null)
        {
            System.out.println(currN.value);
            currN = currN.next;
        }
    }

    //return length
    int getSize()
    {
        return size;
    }


}
