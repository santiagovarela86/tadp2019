describe Framework do

  it "should fail with invariant exception" do
    expect{
      objetoTest = ClaseParaTest.new(1,0,0,0)
    }.to raise_error(InvariantException)
  end

  it "should fail with precondition exception" do
    expect{
      objetoTest = ClaseParaTest.new(1,0,-1,0)
      objetoTest.metodo1
    }.to raise_error(PrecondicionException)
  end

  it "should fail with postcondition exception" do
    expect{
      objetoTest = ClaseParaTest.new(1,0,-1,0)
      objetoTest.metodo2
    }.to raise_error(PostcondicionException)
  end

  it "should fail with precondition exception (dummy pre/post)" do
    expect{
      objetoTest = ClaseParaTest.new(1,0,-1,0)
      objetoTest.metodo3
    }.to raise_error(PrecondicionException)
  end

  it "should fail with postcondition exception (dummy pre/post)" do
    expect{
      objetoTest = ClaseParaTest.new(1,0,-1,0)
      objetoTest.metodo4
    }.to raise_error(PostcondicionException)
  end

  it "should fail when instantiating a stack with a capacity of minus 1" do
    expect{
      objetoTest = Pila.new(-1)
    }.to raise_error(InvariantException)
  end

  it "should fail pushing into a full stack" do
    expect{
      objetoTest = Pila.new(2)
      objetoTest.push(1)
      objetoTest.push(2)
      objetoTest.push(3)
    }.to raise_error(PrecondicionException)
  end

  it "should fail popping from an empty stack" do
    expect{
      objetoTest = Pila.new(2)
      objetoTest.push(1)
      objetoTest.pop
      objetoTest.pop
    }.to raise_error(PrecondicionException)
  end

  it "should fail getting the top of an empty stack" do
    expect{
      objetoTest = Pila.new(2)
      objetoTest.top
    }.to raise_error(PrecondicionException)
  end

  it "should fail dividing by zero" do
    expect{
      objetoTest = Operaciones.new()
      result = objetoTest.dividir(10, 0)
    }.to raise_error(PrecondicionException)
  end

  it "should fail dividing by zero" do
    expect{
      objetoTest = Operaciones.new()
      result = objetoTest.dividir(10, 0)
    }.to raise_error(PrecondicionException)
  end

  it "should fail if the postcondition is false when passing the result of the method as an argument to the postcondition" do
    expect{
      objetoTest = ClaseParaTest.new(1,1,-1,1)
      result = objetoTest.prueboResultEnPostCondicion(10, 1)
    }.to raise_error(PostcondicionException)
  end
end