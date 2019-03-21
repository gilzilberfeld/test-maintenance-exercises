using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;

namespace CalculatorServer
{
    [Route("calculator/")]
    [ApiController]
    public class CalculatorController : ControllerBase
    {
        private readonly ICalculator calculator;

        public CalculatorController(ICalculator calculator)
        {
            this.calculator = calculator;
        }

        [HttpGet("display")]
        public ActionResult<IEnumerable<string>> Get_Display()
        {
            return Content(calculator.Display);
        }

        [HttpPost("press")]
        public void Post_Press([FromQuery] string key)
        {
            calculator.Press(key);
        }

        [HttpPost("reset")]
        public void Post_Reset()
        {
            Calculator.Reset();
        }

        [HttpPost("restore")]
        public void Post_Restore([FromQuery] string user)
        {
            calculator.GetLastValueFor(user);
        }

    }
}
