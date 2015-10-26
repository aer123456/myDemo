/*一些有意思的mocha进度报告主题
 *牛头形状进度报告Nyan
 */飞机飞行样式进度报告Landing Strip


var assert = require("assert");

//describe是可以嵌套的，如下测试Array模块下的#indexOf()子模块。module_name随便取，关键是明了。
describe('Double describe test', function() {
  describe('#indexOf()', function () {
    it('should return -1 when the value is not present', function () {
      assert.equal(-1, [1,2,3].indexOf(5));
      assert.equal(-1, [1,2,3].indexOf(0));
    });
  });
});


//ASYNC HookS， before(), after(), beforeEach(), afterEach()
describe('Hooks test', function() {
  var db = new Connection,
      tobi = new User('tobi'),
      loki = new User('loki'),
      jane = new User('jane');

  beforeEach(function(done) {
    db.clear(function(err) {
      if (err) return done(err);
      db.save([tobi, loki, jane], done);
    });
  });

  describe('#find()', function() {
    it('respond with matching records', function(done) {
      db.find({type: 'User'}, function(err, res) {
        if (err) return done(err);
        res.should.have.length(3);
        done();
      });
    });
  });
});


//Delayed test
setTimeout(function() {
  // do some setup
  describe('Delayed test', function() {
    done();
  });

  run();
}, 5000);


//只跑一组特定的测试，加上.only()方法（it或者describe后面都可以，分别用于一个和一批）， 但是hook还是会跑
describe('Exclusive only one or a group tests', function() {
  describe('#indexOf()', function() {
    it.only('should return -1 unless present', function() {
      // ...
    });

    it('should return the index when present', function() {
      // ...
    });
  });
});


//跳过一个或者一组测试，加上.skip()方法， 用法同上。
describe('Skip one or a group test', function() {
  describe('#indexOf()', function() {
    it.skip('should return -1 unless present', function() {
      // ...
    });

    it('should return the index when present', function() {
      // ...
    });
  });
});


//动态继承
function add() {
  return Array.prototype.slice.call(arguments).reduce(function(prev, curr) {
    return prev + curr;
  }, 0);
}

describe('add()', function() {
  var tests = [
    {args: [1, 2],       expected: 3},
    {args: [1, 2, 3],    expected: 6},
    {args: [1, 2, 3, 4], expected: 10}
  ];

  tests.forEach(function(test) {
    it('correctly adds ' + test.args.length + ' args', function() {
      var res = add.apply(null, test.args);
      assert.equal(res, test.expected);
    });
  });
});


//设置超时Timeout
describe('a suite of tests', function() {
  this.timeout(500);   //整个test的时限是500ms

  it('should take less than 500ms', function(done){
    setTimeout(done, 300); //这个test单元的时限是300ms
  });

  it('should take less than 500ms as well', function(done){
    setTimeout(done, 200);
  });
});



//INTERFACE
//BDD context=describe  其它如it(), before(), after(), beforeEach(), and afterEach().
describe('interface BDD', function() {
    before(function() {
      // ...
    });

    describe('#indexOf()', function() {
      context('when not present', function() {
        it('should not throw an error', function() {
          (function() {
            [1,2,3].indexOf(4);
          }).should.not.throw();
        });
        it('should return -1', function() {
          [1,2,3].indexOf(4).should.equal(-1);
        });
      });
      context('when present', function() {
        it('should return the index where the element first appears in the array', function() {
          [1,2,3].indexOf(3).should.equal(2);
        });
      });
    });
  });


//Exports
module.exports = {
  before: function() {
    // ...
  },

  'Exports': {
    '#indexOf()': {
      'should return -1 when not present': function() {
        [1,2,3].indexOf(4).should.equal(-1);
      }
    }
  }
};


//Require， requirejs的用法，除了申请mocha的一些模块之外， 也可以请求文件之外的模块。
var testCase = require('mocha').describe;
var pre = require('mocha').before;
var assertions = require('mocha').it;
var assert = require('assert');

testCase('Require', function() {
  pre(function() {
    // ...
  });

  testCase('#indexOf()', function() {
    assertions('should return -1 when not present', function() {
      assert.equal([1,2,3].indexOf(4), -1);
    });
  });
});
