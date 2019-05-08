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

end