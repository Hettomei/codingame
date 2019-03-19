const { expect } = require('chai');
const ascii = require('../ascii');


describe('Array', () => {
  describe('#indexOf()', () => {
    it('return -1 when the value is not present', () => {
      expect([1, 2, 3].indexOf(4)).to.equal(-1);
    });

    it('return undefined when the value is not present', () => {
      const a = [1, 2, 3];
      expect(a[55]).to.equal(undefined);
    });
  });

  describe('le fichier est chargé', () => {
    it('la fonction retourne "toto"', () => {
      expect(ascii.toto()).to.equal('toto');
    });
  });

  describe('.extract', () => {
    it('découpe sur l index', () => {
      const str = '123456789';
      const index = 0;
      const width = 4;
      expect(ascii.extract(str, index, width)).to.equal('1234');
    });

    it('découpe sur l index', () => {
      const str = '123-234-345-456-567';
      const index = 2;
      const width = 4;
      expect(ascii.extract(str, index, width)).to.equal('345-');
    });

    it('cas reel : decoupe', () => {
      const str = ' #  ##   ## ##  ### ###  ## # # ###  ## # # #   # # ###  #  ##   #  ##   ## ### # # # # # # # # # # ### ### ';
      const index = 5;
      const width = 4;
      expect(ascii.extract(str, index, width)).to.equal('### ');
    });

    it('découpe sur l index', () => {
      const str = '12345-67890-abcdef';
      const index = 1;
      const width = 6;
      expect(ascii.extract(str, index, width)).to.equal('67890-');
    });
  });
});
