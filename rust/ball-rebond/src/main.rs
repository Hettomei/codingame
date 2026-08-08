struct Ball {
x: f32,
    y: f32,
}

impl Ball {
    fn new(x: f32, y: f32) -> Ball {
        Ball { x, y }
    }

    fn mmove(&mut self, dx: f32) {
        self.x += dx;
    }

    fn display_position(self: &Ball) {
        println!("x:{:.5}, y:{:.5}", self.x, self.y)
    }
}

fn main() {
    println!("Hello, world");

    let mut b = Ball::new(5.0, 10.0);

    b.display_position();
    b.mmove(0.5);
    b.display_position();
}
