import java.util.ArrayList;

/**
 * Snake class
 */
public class Snake
{
    //The direction of movement of the snake
    private SnakeDirection direction;
    //Status - the snake alive or not.
    private boolean isAlive;
    //List of snake pieces.
    private ArrayList<SnakeSection> sections = new ArrayList<SnakeSection>();

    public Snake(int x, int y)
    {
        sections = new ArrayList<SnakeSection>();
        sections.add(new SnakeSection(x, y));
        isAlive = true;
    }

    public boolean isAlive()
    {
        return isAlive;
    }

    public int getX()
    {
        return sections.get(0).getX();
    }

    public int getY()
    {
        return sections.get(0).getY();
    }

    public SnakeDirection getDirection()
    {
        return direction;
    }

    public void setDirection(SnakeDirection direction)
    {
        this.direction = direction;
    }

    public ArrayList<SnakeSection> getSections()
    {
        return sections;
    }

    /**
     * 
        The method moves the snake one move.
      * The direction of movement is set to a variable direction.
     */
    public void move()
    {
        if (!isAlive) return;

        if (direction == SnakeDirection.UP)
            move(0, -1);
        else if (direction == SnakeDirection.RIGHT)
            move(1, 0);
        else if (direction == SnakeDirection.DOWN)
            move(0, 1);
        else if (direction == SnakeDirection.LEFT)
            move(-1, 0);
    }

    /**
     * The method moves the snake in the next cell.
      * Kordinaty cells defined from the current head via variable (dx, dy).
     */
    private void move(int dx, int dy)
    {
        //Create a new head - a new "piece of the snake."
        SnakeSection head = sections.get(0);
        head = new SnakeSection(head.getX() + dx, head.getY() + dy);

        //Check - whether the head climbed abroad room
        checkBorders(head);
        if (!isAlive) return;

        //Check - Do not cross the snake itself
        checkBody(head);
        if (!isAlive) return;

        //Check - whether the snake ate the mouse.
        Mouse mouse = Room.game.getMouse();
        if (head.getX() == mouse.getX() && head.getY() == mouse.getY()) // ate
        {
            sections.add(0, head);                  //Add new head
            Room.game.eatMouse();                   //The tail does not remove, but create a new mouse.
        }
        else //просто движется
        {
            sections.add(0, head);                  //We added a new head
            sections.remove(sections.size() - 1);   //removed the last element from the tail
        }
    }

    /**
     *  The method checks - whether a new head within the room
     */
    private void checkBorders(SnakeSection head)
    {
        if ((head.getX() < 0 || head.getX() >= Room.game.getWidth()) || head.getY() < 0 || head.getY() >= Room.game.getHeight())
        {
            isAlive = false;
        }
    }

    /**
     *  The method checks - whether head matches with any portion of the snake's body.
     */
    private void checkBody(SnakeSection head)
    {
        if (sections.contains(head))
        {
            isAlive = false;
        }
    }
}
