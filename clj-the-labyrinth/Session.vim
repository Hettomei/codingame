let SessionLoad = 1
let s:so_save = &so | let s:siso_save = &siso | set so=0 siso=0
let v:this_session=expand("<sfile>:p")
silent only
cd ~/programmes/codingame/clj-the-labyrinth
if expand('%') == '' && !&modified && line('$') <= 1 && getline(1) == ''
  let s:wipebuf = bufnr('%')
endif
set shortmess=aoO
badd +5 src/clj_the_labyrinth/core.clj
badd +1 ~/programmes/codingame/clj-the-labyrinth
badd +0 test/clj_the_labyrinth/core_test.clj
argglobal
silent! argdel *
argadd src/clj_the_labyrinth/core.clj
edit src/clj_the_labyrinth/core.clj
set splitbelow splitright
wincmd _ | wincmd |
vsplit
1wincmd h
wincmd w
wincmd t
set winminheight=1 winminwidth=1 winheight=1 winwidth=1
exe 'vert 1resize ' . ((&columns * 78 + 79) / 158)
exe 'vert 2resize ' . ((&columns * 79 + 79) / 158)
argglobal
setlocal fdm=syntax
setlocal fde=0
setlocal fmr={{{,}}}
setlocal fdi=#
setlocal fdl=10
setlocal fml=1
setlocal fdn=20
setlocal fen
7
normal! zo
let s:l = 10 - ((3 * winheight(0) + 18) / 37)
if s:l < 1 | let s:l = 1 | endif
exe s:l
normal! zt
10
normal! 033|
wincmd w
argglobal
edit test/clj_the_labyrinth/core_test.clj
setlocal fdm=syntax
setlocal fde=0
setlocal fmr={{{,}}}
setlocal fdi=#
setlocal fdl=10
setlocal fml=1
setlocal fdn=20
setlocal fen
1
normal! zo
5
normal! zo
6
normal! zo
7
normal! zo
7
normal! zo
10
normal! zo
11
normal! zo
12
normal! zo
13
normal! zo
13
normal! zo
let s:l = 13 - ((11 * winheight(0) + 18) / 37)
if s:l < 1 | let s:l = 1 | endif
exe s:l
normal! zt
13
normal! 03|
lcd ~/programmes/codingame/clj-the-labyrinth
wincmd w
2wincmd w
exe 'vert 1resize ' . ((&columns * 78 + 79) / 158)
exe 'vert 2resize ' . ((&columns * 79 + 79) / 158)
tabnext 1
if exists('s:wipebuf') && getbufvar(s:wipebuf, '&buftype') isnot# 'terminal'
  silent exe 'bwipe ' . s:wipebuf
endif
unlet! s:wipebuf
set winheight=1 winwidth=20 winminheight=1 winminwidth=1 shortmess=filnxtToO
let s:sx = expand("<sfile>:p:r")."x.vim"
if file_readable(s:sx)
  exe "source " . fnameescape(s:sx)
endif
let &so = s:so_save | let &siso = s:siso_save
doautoall SessionLoadPost
unlet SessionLoad
" vim: set ft=vim :
