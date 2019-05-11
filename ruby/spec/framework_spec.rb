describe MyMixin, '#invariants  ' do

  it 'debería fallar ' do
    some_instance = SomeTestClass.new(1, 11)
    some_instance.test_method
    expect {some_instance.test_method_2}.to raise_error InvariantError
  end

  it 'after proc working with instance variables' do
    some_instance = SomeTestClass.new(1, 11)
    some_instance.test_method
    expect(some_instance.fuerza).to eq(12)
  end

  it 'pre error' do
    some_instance = SomeTestClass.new(1, 11)
    expect {some_instance.add_vida(-1)}.to raise_error PreError
  end

  it 'pre error 2' do
    division = Operaciones.new
    expect {division.dividir(10, 0)}.to raise_error PreError
  end

  it 'pre no error' do
    division = Operaciones.new
    expect(division.dividir(10, 1)).to eq 10
  end

  it 'post' do
    suma = Operaciones.new
    expect {suma.suma(10, 2)}.to raise_error PostError

  end

end