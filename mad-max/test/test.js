/* global describe it */

const { expect } = require('chai');
const i = require('../index');

describe('index', () => {
  describe('.square()', () => {
    it('square', () => {
      expect(i.square(2.5)).to.equal(6.25);
    });
  });

  describe('.toObj()', () => {
    it('to object', () => {
      const input = i.toObj([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]);
      const expected = {
        id: 1,
        type: 2,
        player: 3,
        mass: 4,
        radius: 5,
        x: 6,
        y: 7,
        vx: 8,
        vy: 9,
        water: 10,
        extra2: 11,
      };

      expect(input).to.deep.equal(expected);
    });
  });

  describe('.distance()', () => {
    it('distance', () => {
      [
        [{ x: 0, y: 10 }, { x: 0, y: 10 }, 0],
        [{ x: 0, y: 0 }, { x: 0, y: 10 }, 10],
        [{ x: 10, y: 0 }, { x: 10, y: 10 }, 10],
        [{ x: 0, y: 0 }, { x: 10, y: 10 }, 14],
      ].forEach(([a, b, expected]) => {
        expect(Math.round(i.distance(a, b))).to.equal(expected);
      });
    });
  });
});
