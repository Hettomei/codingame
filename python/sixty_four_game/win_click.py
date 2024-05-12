"""
taken directly from https://github.com/asweigart/pyautogui/blob/master/pyautogui/_pyautogui_win.py
"""

import time
import math
import ctypes
import msvcrt
import ctypes.wintypes

import sys

# pylint: disable=invalid-name, missing-class-docstring, too-few-public-methods

if sys.platform != "win32":
    # pylint: disable-next=broad-exception-raised
    raise Exception(
        "The pyautogui_win module should only be loaded on a Windows system."
    )


# Fixes the scaling issues where PyAutoGUI was reporting the wrong resolution:
try:
    ctypes.windll.user32.SetProcessDPIAware()
except AttributeError:
    pass  # Windows XP doesn't support this, so just do nothing.


MOUSEEVENTF_LEFTDOWN = 0x0002
MOUSEEVENTF_LEFTUP = 0x0004
MOUSEEVENTF_LEFTCLICK = MOUSEEVENTF_LEFTDOWN + MOUSEEVENTF_LEFTUP


# These ctypes structures are for Win32 INPUT, MOUSEINPUT, KEYBDINPUT, and HARDWAREINPUT structures,
# used by SendInput and documented here: http://msdn.microsoft.com/en-us/library/windows/desktop/ms646270(v=vs.85).aspx
# Thanks to BSH for this StackOverflow answer: https://stackoverflow.com/questions/18566289/how-would-you-recreate-this-windows-api-structure-with-ctypes
class MOUSEINPUT(ctypes.Structure):
    _fields_ = [
        ("dx", ctypes.wintypes.LONG),
        ("dy", ctypes.wintypes.LONG),
        ("mouseData", ctypes.wintypes.DWORD),
        ("dwFlags", ctypes.wintypes.DWORD),
        ("time", ctypes.wintypes.DWORD),
        ("dwExtraInfo", ctypes.POINTER(ctypes.wintypes.ULONG)),
    ]


def _size():
    """Returns the width and height of the screen as a two-integer tuple.

    Returns:
      (width, height) tuple of the screen size, in pixels.
    """
    return (
        ctypes.windll.user32.GetSystemMetrics(0),
        ctypes.windll.user32.GetSystemMetrics(1),
    )


def _position():
    """Returns the current xy coordinates of the mouse cursor as a two-integer
    tuple by calling the GetCursorPos() win32 function.

    Returns:
      (x, y) tuple of the current xy coordinates of the mouse cursor.
    """

    cursor = ctypes.wintypes.POINT()
    ctypes.windll.user32.GetCursorPos(ctypes.byref(cursor))
    return (cursor.x, cursor.y)


def _sendMouseEvent(ev, x, y, count=1000):
    """The helper function that actually makes the call to the mouse_event()
    win32 function.

    Args:
      ev (int): The win32 code for the mouse event. Use one of the MOUSEEVENTF_*
      constants for this argument.
      x (int): The x position of the mouse event.
      y (int): The y position of the mouse event.
      dwData (int): The argument for mouse_event()'s dwData parameter. So far
        this is only used by mouse scrolling.

    Returns:
      None
    """
    # ARG! For some reason, SendInput isn't working for mouse events.
    # I'm switching to using the older mouse_event win32 function.
    # mouseStruct = MOUSEINPUT()
    # mouseStruct.dx = x
    # mouseStruct.dy = y
    # mouseStruct.mouseData = ev
    # mouseStruct.time = 0
    # mouseStruct.dwExtraInfo = ctypes.pointer(ctypes.c_ulong(0)) # according to https://stackoverflow.com/questions/13564851/generate-keyboard-events I can just set this. I don't really care about this value.
    # inputStruct = INPUT()
    # inputStruct.mi = mouseStruct
    # inputStruct.type = INPUT_MOUSE
    # ctypes.windll.user32.SendInput(1, ctypes.pointer(inputStruct), ctypes.sizeof(inputStruct))

    dwData = 0
    width, height = _size()
    convertedX = 65536 * x // width + 1
    convertedY = 65536 * y // height + 1

    cx = ctypes.c_long(convertedX)
    cy = ctypes.c_long(convertedY)
    for _ in range(1, count):
        ctypes.windll.user32.mouse_event(ev, cx, cy, dwData, 0)


def exit_if(killx, killy):
    """
    l
    """
    if killx <= 15 or killy <= 15:
        print("STOP")
        sys.exit(0)


def wait():
    """
    w
    """
    print("wait mode")
    while True:
        movex, movey = _position()
        # print(f"{movex}.{movey} ", end="", flush=True)

        time.sleep(0.2)
        newx, newy = _position()
        exit_if(newx, newy)

        if newy != movey or newx != movex:
            return


def click():
    """
    run
    """
    while True:
        movex, movey = _position()

        time.sleep(0.3)
        newx, newy = _position()
        exit_if(newx, newy)
        if newy == movey or newx == movex:
            wait()
            print("click mode")
        else:
            _sendMouseEvent(MOUSEEVENTF_LEFTCLICK, newx, newy, count=20)

        # go back to previous place
        # ctypes.windll.user32.SetCursorPos(movex, movey)



# while True:
#     time.sleep(1)
#     # print(".", end="", flush=True)
#     # if msvcrt.kbhit():
#     print(". ", end="", flush=True)
#     kp = msvcrt.getch()
#     print(kp, end="", flush=True)

print("sleep 3")
time.sleep(3)
click()
