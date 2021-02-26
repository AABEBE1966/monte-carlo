import pandas as pd
import os
from sys import argv


from multiprocessing import Pool, Process,Manager,Array
import matplotlib.pyplot as plt
#plt.use("TkAgg")

import matplotlib.patches as mpatches

from mpl_toolkits import mplot3d
from mpl_toolkits.mplot3d import Axes3D
import numpy as np
import matplotlib.colors as mcolors
class PlotGraph:
    def __init__(self,structure_file):
        self.str=structure_file

    def plot_graph(self):
        name=self.str.split("/")[-1]
        name_without_csv=name.split(".")[0]

        str_file=pd.read_csv(self.str, header=None)
        fig = plt.figure(figsize=(5,4))
        ax = fig.add_subplot(111, projection='3d')
        ax.scatter(str_file[0], str_file[1], str_file[2],s=10,c=str_file[3],
                   cmap=mcolors.ListedColormap(["blue","red"]))
        classes = ['Acceptor','Donor']
        class_colours = ['r','b']
        recs = []
        for i in range(0,len(class_colours)):
            recs.append(mpatches.Rectangle((0,0),1,1,fc=class_colours[i]))
        plt.legend(recs,classes,loc=1)
        plt.title(name_without_csv)
        plt.savefig(os.path.join(self.str.rsplit("/",1)[0], name_without_csv+".png"))
        plt.show()

if __name__ == '__main__':
    print(argv[0])
    print(argv[1])
    str_file=argv[1]
    plotGraph=PlotGraph(str_file)
    plotGraph.plot_graph()
