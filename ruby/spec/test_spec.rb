describe Framework do

  it "should fail when creating with minus 1 capacity" do
    expect{
      pila = Pila.new(-1)
    }.to raise_error(InvariantException)
  end

end