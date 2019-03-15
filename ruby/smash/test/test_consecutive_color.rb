require "test/unit"
require "pp"
require_relative "../consecutive_color"

class TestConsecutiveColor < Test::Unit::TestCase

  def test_count
    array = [
      %W/. . 4 . . ./,
      %W/. . 3 . . ./,
      %W/. . 3 . . ./,
    ]

    [0, 1, 3, 4, 5].each do |i|
      ca = ConsecutiveColor.new(array,i)
      assert_equal(0, ca.count)
    end
    ca = ConsecutiveColor.new(array,2)
    assert_equal(2, ca.count)
  end

  def test_count_2
    array = [
      %W/. . 4 . . ./,
      %W/. . 4 . . ./,
      %W/. . 3 . . ./,
    ]

    ca = ConsecutiveColor.new(array,2)
    assert_equal(2, ca.count)

    array = [
      %W/. . 4 4 4 ./,
      %W/. . 4 . 4 4/,
      %W/. . 3 . . 4/,
    ]

    ca = ConsecutiveColor.new(array,2)

    assert_equal(7, ca.count)
  end

  def test_start_position
    array = [
      %W/. . . . . ./,
      %W/. . 4 . . ./,
      %W/. . 3 . . ./,
    ]

    ca = ConsecutiveColor.new(array,2)
    assert_equal(2, ca.start_position.x)
    assert_equal(2, ca.start_position.y)
  end
end
