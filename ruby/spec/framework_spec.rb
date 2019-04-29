
  describe MyMixin, '#invariants  ' do
    it 'debería fallar ' do
      someInstance = SomeTestClass.new
      someInstance.test_method
      someInstance.test_method
      expect{someInstance.test_method_2}.to raise_error InvariantError
    end
  end