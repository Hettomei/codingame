library ieee;
use ieee.std_logic_1164.all;

entity multiplexeur_5_en_1 is
port(
	a,b,c,d,e : in std_logic_vector(2 downto 0) ;
	sel : in std_logic_vector(2 downto 0);
	N : out std_logic_vector (2 downto 0 ));
end multiplexeur_5_en_1;

architecture arch_multiplexeur_5_en_1 of multiplexeur_5_en_1 is
begin
N <= a when SEL = "000" else
	 b when SEL = "001" else
	 c when SEL = "010" else
  	 d when SEL = "011" else
	 e when SEL = "100" ;
end arch_multiplexeur_5_en_1;

library ieee;
use ieee.std_logic_1164.all;
package multiplexeur_5_en_1_package is
component multiplexeur_5_en_1
port (
	a,b,c,d,e : in std_logic_vector(2 downto 0) ;
	sel : in std_logic_vector(2 downto 0);
	N : out std_logic_vector (2 downto 0 )
	);
end component;
end multiplexeur_5_en_1_package;