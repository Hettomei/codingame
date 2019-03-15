require "test/unit"
require "pp"
require_relative "../simule_game"

class TestSimuleGame < Test::Unit::TestCase

  def test_simule
    array = [
      %W/1 2 . . . ./,
      %W/. . . . . ./,
      %W/. . . . . ./,
    ]
    sg = SimuleGame.new(array)

    array_output = [
      %W/. . . . . ./,
      %W/. . . . . ./,
      %W/1 2 . . . ./,
    ]
    # assert_equal(array_output, sg.simule)
  end

  def test_simule2
    array = [
      %W/1 2 . . . ./,
      %W/2 2 . . . ./,
      %W/2 . . . . ./,
    ]
    sg = SimuleGame.new(array)

    array_output = [
      %W/. . . . . ./,
      %W/. . . . . ./,
      %W/1 . . . . ./,
    ]
    assert_equal(array_output, sg.simule)
  end

end
