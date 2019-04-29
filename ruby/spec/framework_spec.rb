
  describe MyMixin, '#invariants  ' do
    it 'debería fallar ' do
      some_instance = SomeTestClass.new
      some_instance.test_method
      some_instance.test_method
      expect{some_instance.test_method_2}.to raise_error InvariantError
    end
  end