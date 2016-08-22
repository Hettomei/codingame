let SessionLoad = 1
let s:so_save = &so | let s:siso_save = &siso | set so=0 siso=0
let v:this_session=expand("<sfile>:p")
silent only
cd ~/programmes/codingame/clj-defibrillator
if expand('%') == '' && !&modified && line('$') <= 1 && getline(1) == ''
  let s:wipebuf = bufnr('%')
endif
set shortmess=aoO
badd +16 src/clj_defibrillator/core.clj
badd +6 src/clj_defibrillator/test1.txt
badd +18 ~/programmes/codingame/clj-ascii/src/clj_ascii/core.clj
badd +1 test1.txt
badd +13 test2.txt
badd +0 test3.txt
badd +0 term://.//1973:/bin/bash
argglobal
silent! argdel *
argadd src/clj_defibrillator/core.clj
edit src/clj_defibrillator/core.clj
set splitbelow splitright
wincmd _ | wincmd |
vsplit
1wincmd h
wincmd w
wincmd _ | wincmd |
split
1wincmd k
wincmd w
wincmd t
set winheight=1 winwidth=1
exe 'vert 1resize ' . ((&columns * 52 + 57) / 115)
exe '2resize ' . ((&lines * 11 + 13) / 26)
exe 'vert 2resize ' . ((&columns * 62 + 57) / 115)
exe '3resize ' . ((&lines * 12 + 13) / 26)
exe 'vert 3resize ' . ((&columns * 62 + 57) / 115)
argglobal
setlocal fdm=manual
setlocal fde=0
setlocal fmr={{{,}}}
setlocal fdi=#
setlocal fdl=10
setlocal fml=1
setlocal fdn=20
setlocal fen
silent! normal! zE
1,2fold
let s:l = 11 - ((10 * winheight(0) + 12) / 24)
if s:l < 1 | let s:l = 1 | endif
exe s:l
normal! zt
11
normal! 0
wincmd w
argglobal
edit test1.txt
setlocal fdm=manual
setlocal fde=0
setlocal fmr={{{,}}}
setlocal fdi=#
setlocal fdl=10
setlocal fml=1
setlocal fdn=20
setlocal fen
silent! normal! zE
let s:l = 1 - ((0 * winheight(0) + 5) / 11)
if s:l < 1 | let s:l = 1 | endif
exe s:l
normal! zt
1
normal! 0
wincmd w
argglobal
edit term://.//1973:/bin/bash
setlocal fdm=manual
setlocal fde=0
setlocal fmr={{{,}}}
setlocal fdi=#
setlocal fdl=10
setlocal fml=1
setlocal fdn=20
setlocal fen
let s:l = 214 - ((8 * winheight(0) + 6) / 12)
if s:l < 1 | let s:l = 1 | endif
exe s:l
normal! zt
214
normal! 026|
wincmd w
exe 'vert 1resize ' . ((&columns * 52 + 57) / 115)
exe '2resize ' . ((&lines * 11 + 13) / 26)
exe 'vert 2resize ' . ((&columns * 62 + 57) / 115)
exe '3resize ' . ((&lines * 12 + 13) / 26)
exe 'vert 3resize ' . ((&columns * 62 + 57) / 115)
tabnext 1
if exists('s:wipebuf') && getbufvar(s:wipebuf, '&buftype') isnot# 'terminal'
  silent exe 'bwipe ' . s:wipebuf
endif
unlet! s:wipebuf
set winheight=1 winwidth=20 shortmess=filnxtToO
let s:sx = expand("<sfile>:p:r")."x.vim"
if file_readable(s:sx)
  exe "source " . fnameescape(s:sx)
endif
let &so = s:so_save | let &siso = s:siso_save
doautoall SessionLoadPost
unlet SessionLoad
" vim: set ft=vim :
