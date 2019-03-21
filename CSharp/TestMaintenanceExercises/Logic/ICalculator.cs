namespace CalculatorServer
{
    public interface ICalculator
    {
        string Display { get; }

        void GetLastValueFor(string userName);
        void Press(string key);
    }
}