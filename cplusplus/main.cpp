#include <iostream>
#include <cstdlib> // for EXIT_SUCCESS and EXIT_FAILURE

int returnFive()
{
    return 5;
}
int main() {
  std::cout << "ok" << returnFive <<   '\n';
  return EXIT_SUCCESS;
}
