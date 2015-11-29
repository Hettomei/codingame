def get arr
  @hauteur.times do |i|
    a = ""
    arr.each do |n|
      a << @ascii[i].chars.each_slice(@largeur).to_a[n].join()
    end
    p a
  end
end

@largeur = 4
@hauteur = 5
@str = "ABC"
@ascii = []
@ascii << " #  ##   ## ##  ### ###  ## # # ###  ## # # #   # # ###  #  ##   #  ##   ## ### # # # # # # # # # # ### ### "
@ascii << "# # # # #   # # #   #   #   # #  #    # # # #   ### # # # # # # # # # # #    #  # # # # # # # # # #   #   # "
@ascii << "### ##  #   # # ##  ##  # # ###  #    # ##  #   ### # # # # ##  # # ##   #   #  # # # # ###  #   #   #   ## "
@ascii << "# # # # #   # # #   #   # # # #  #  # # # # #   # # # # # # #    ## # #   #  #  # # # # ### # #  #  #       "
@ascii << "# # ##   ## ##  ### #    ## # # ###  #  # # ### # # # #  #  #     # # # ##   #  ###  #  # # # #  #  ###  #  "

val = @str.codepoints.map { |d| d - 65}
get(val)
