package sjsu.umlparser.TestCases.First;


public class Tester {

    public static int main(String[] args)
    {
        Component obj = new ConcreteDecoratorB( new ConcreteDecoratorA( new ConcreteComponent() ) ) ;
        String result = obj.operation() ;
   //     test(1);
        System.out.println( result );
        
        return 1;
		
    }

    public static double test(int a)
    {
    	System.out.println("TEssss"+a);
    	return 1.2;
    }

}

