require "test/unit"
require "pp"
require_relative "../chute"

class TestChute < Test::Unit::TestCase

  def merge(a, ca, cb)
    a[0][0] = ca
    a[0][1] = cb
    a
  end

  def test_algo
    a = [
      %W/. . . . . ./,
      %W/. . . . . ./,
      %W/1 2 . . . ./,
    ]

    array_output = [
      %W/3 4 . . . ./,
      %W/. . . . . ./,
      %W/1 2 . . . ./,
    ]
    assert_equal(array_output, merge(a, "3", "4"))
  end

  def test_next1
    array = [
      %W/1 2 . . . ./,
      %W/. . . . . ./,
      %W/. . . . . ./,
    ]
    sg = Chute.new(array)

    array_output = [
      %W/. . . . . ./,
      %W/. . . . . ./,
      %W/1 2 . . . ./,
    ]
    assert_equal(array_output, sg.chute)
  end

  def test_next2
    array = [
      %W/1 2 . . . 3/,
      %W/. . . 5 4 ./,
      %W/. 4 . 3 . ./,
    ]
    sg = Chute.new(array)

    array_output = [
      %W/. . . . . ./,
      %W/. 2 . 5 . ./,
      %W/1 4 . 3 4 3/,
    ]
    assert_equal(array_output, sg.chute)
  end

  def test_next3
    array = [
      %W/1 2 . . . ./,
      %W/. 2 . . . ./,
      %W/. . . . . ./,
    ]
    sg = Chute.new(array)

    array_output = [
      %W/. . . . . ./,
      %W/. 2 . . . ./,
      %W/1 2 . . . ./,
    ]
    assert_equal(array_output, sg.chute)
  end

  def test_next4
    array = [
      %W/1 2 . . . ./,
      %W/. 2 . . . ./,
      %W/. . . . . ./,
      %W/. . . . . ./,
      %W/. . . . . ./,
    ]
    sg = Chute.new(array)

    array_output = [
      %W/. . . . . ./,
      %W/. . . . . ./,
      %W/. . . . . ./,
      %W/. 2 . . . ./,
      %W/1 2 . . . ./,
    ]
    assert_equal(array_output, sg.chute)
  end
end
