require 'test/unit'
require './roman'

class RomanTest < Test::Unit::TestCase

  include Roman

  def test_simple_word
    assert_equal("I", to_roman(1))
    assert_equal("II", to_roman(2))
    assert_equal("III", to_roman(3))
    assert_equal("IIII", to_roman(4))
    assert_equal("V", to_roman(5))
    assert_equal("VI", to_roman(6))
  end

end
