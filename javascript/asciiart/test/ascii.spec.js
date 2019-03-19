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
});
