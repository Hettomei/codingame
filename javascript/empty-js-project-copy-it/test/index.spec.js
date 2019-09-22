const { expect } = require("chai");
const { a } = require("../index");

describe("index", () => {
  describe(".a()", () => {
    it("return 4", () => {
      expect(a()).to.equal(4);
    });
  });
});
