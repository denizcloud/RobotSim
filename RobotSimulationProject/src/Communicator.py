import socket
import pygame
import time

def client_program():
    host = socket.gethostname()
    port = 3131

    client_socket = socket.socket()
    client_socket.connect((host, port))

    pygame.joystick.init()
    pygame.display.init()
    joystick = pygame.joystick.Joystick(0)
    while True:
        pygame.event.pump()
        print(joystick.get_axis(1))
        #print(joystick.get_axis(2))
        print(joystick.get_axis(3))
        client_socket.send(str(joystick.get_axis(1)).encode())
        client_socket.send(("!").encode())
        client_socket.send(str(joystick.get_axis(3)).encode())
        client_socket.send(("?").encode())
        time.sleep(1)
    #print(client_socket.recv(1024).decode())




if __name__ == '__main__':

    client_program()
