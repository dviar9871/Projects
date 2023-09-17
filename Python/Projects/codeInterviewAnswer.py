import matplotlib.pyplot as plt
import numpy as np
import pandas as pds
import openpyxl


class long_jumper:
    _distances = []
    _average_jump_distance = 0.0
    _name = ""

    def __init__(self, name):
        self._name = name

    def __repr__(self) -> str:
        return f'long_jumper (Name:"{self._name}", Average Jump Distance: "{self._average_jump_distance}", ' \
               f'Distances: "{self._distances}") '

    def get_name(self):
        return self._name

    def add_distance(self, list_of_distances):
        self._distances = list_of_distances

    def get_distance(self):
        return self._distances

    def get_average_jump_distance(self):
        return self._average_jump_distance

    def calculate_average_jump_distance(self):
        for distance in self._distances:
            self._average_jump_distance += float(distance)

        self._average_jump_distance /= len(self._distances)

        return self._average_jump_distance


def main():
    file = pds.read_excel("Long jump data.xlsx")
    list_of_jumpers = []
    names_of_junpers = []

    for i in range(len(file)):
        jumper = long_jumper(file.columns[i])

        jumper.add_distance(list_of_distances=file[file.columns[i]].tolist())

        list_of_jumpers.append(jumper.calculate_average_jump_distance())
        names_of_junpers.append(jumper.get_name())

    array_of_jumpers = np.asarray(list_of_jumpers)
    array_of_names = np.asarray(names_of_junpers)
    fig = plt.figure(figsize=(10, 5))

    # creating the bar plot
    plt.bar(array_of_names, array_of_jumpers,
            width=0.4)

    plt.xlabel("Jumpers")
    plt.ylabel("Average Score")
    plt.title("Data on Long Jumpers")
    plt.show()



main()
