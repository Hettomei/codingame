# pylint: disable=global-statement
"""
cookie clicker
"""

import argparse
import sys
import time

import pyautogui

T_1_SECONDS = 1
T_5_SECONDS = 5
T_10_SECONDS = 10
T_20_MILLI_SECONDS = 0.02
T_500_MILLI_SECONDS = 0.5

def wait(sec, dot=False):
    """
    do nothing
    """
    if dot:
        print(".", end="", flush=True)
    else:
        print(f"wait {sec}s")
    time.sleep(sec)


def pos(silent=True):
    """
    do nothing
    """
    x, y = pyautogui.position()
    if not silent:
        print(f"x: {x} - y: {y}")
    return x, y


def run():
    """
    run
    """
    wait(2)
    while True:
        pyautogui.click()


run()
