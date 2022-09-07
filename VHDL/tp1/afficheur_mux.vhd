library ieee;
use ieee.std_logic_1164.all;
use work.afficheur_hex0_package.all;
use work.multiplexeur_5_en_1_package.all;

entity afficheur_mux is
port(
	A,B,C,D,E :in std_logic_vector (2 downto 0);
	SEL : in std_logic_vector (2 downto 0);
	SEG : out std_logic_vector ( 0 to 6)
	);
	
end afficheur_mux;

architecture arch_afficheur_mux of afficheur_mux is
signal N: std_logic_vector (2 downto 0);
begin
etage0:multiplexeur_5_en_1 port map (A,B,C,D,E,SEL,N);
etage1:afficheur_hex0 port map (N, SEG);
end arch_afficheur_mux;
