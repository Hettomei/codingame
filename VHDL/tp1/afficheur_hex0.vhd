library ieee;
use ieee.std_logic_1164.all;

entity afficheur_hex0 is
port(
	n : in std_logic_vector (2 downto 0 );
	seg : out std_logic_vector(0 to 6)
	);
	
end afficheur_hex0;

architecture arch_afficheur_hex0 of afficheur_hex0 is
begin
process(n)
	begin case n is
		when "000" => seg <= "1001000" ;
	 	when "001" => seg <= "0110000" ;
	 	when "010" => seg <= "1110001" ;
		when "011" => seg <= "1110001" ;
  	 	when "100" => seg <= "0000001" ;
		when others => seg <= "1111111";
		end case;
	end process;
end arch_afficheur_hex0;


library ieee;
use ieee.std_logic_1164.all;
package afficheur_hex0_package is
component afficheur_hex0
port(
	n : in std_logic_vector (2 downto 0 );
	seg : out std_logic_vector(0 to 6)
	);
end component;
end afficheur_hex0_package;