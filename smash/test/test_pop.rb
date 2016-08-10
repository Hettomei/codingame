require "test/unit"
require "pp"
require_relative "../pop"

class TestPop < Test::Unit::TestCase

  def test_pop
    array = [
      %W/. . . . . ./,
      %W/. . . . . ./,
      %W/1 2 . . . ./,
    ]
    pop = Pop.new(array)

    array_output = [
      %W/. . . . . ./,
      %W/. . . . . ./,
      %W/1 2 . . . ./,
    ]
    assert_equal(array_output, pop.pop)
  end

  def test_next2
    array = [
      %W/. . . 1 . 3/,
      %W/. 2 1 5 . 3/,
      %W/1 1 1 3 3 3/,
    ]
    pop = Pop.new(array)

    array_output = [
      %W/. . . 1 . ./,
      %W/. 2 . 5 . ./,
      %W/. . . . . ./,
    ]
    assert_equal(array_output, pop.pop)
  end

  def test_next3
    array = [
      %W/. . . 1 . 3/,
      %W/3 2 3 5 . 3/,
      %W/1 1 1 1 3 4/,
    ]
    pop = Pop.new(array)

    array_output = [
      %W/. . . 1 . 3/,
      %W/3 2 3 5 . 3/,
      %W/. . . . 3 4/,
    ]
    assert_equal(array_output, pop.pop)
  end

  def test_next4
    array = [
      %W/. . . 1 . 3/,
      %W/3 2 3 5 . 3/,
      %W/0 0 0 0 3 4/,
    ]
    pop = Pop.new(array)

    array_output = [
      %W/. . . 1 . 3/,
      %W/3 2 3 5 . 3/,
      %W/0 0 0 0 3 4/,
    ]
    assert_equal(array_output, pop.pop)
  end

  def test_next5
    array = [
      %W/1 . . . . ./,
      %W/2 2 . . . ./,
      %W/2 2 . . . ./,
    ]
    pop = Pop.new(array)

    array_output = [
      %W/1 . . . . ./,
      %W/. . . . . ./,
      %W/. . . . . ./,
    ]
    assert_equal(array_output, pop.pop)
  end
end
